package com.example.LoginPage.OneTimePassword.OTPservice;
import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.UserRepository;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OnBoarding.LastState;
import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.FailedToLoginException;
import com.example.LoginPage.OneTimePassword.OTPcontroller.GlobalResponse;
import com.example.LoginPage.OneTimePassword.OTPcontroller.OtpService;
import com.example.LoginPage.OneTimePassword.OTPcontroller.VerifyOtpEnum;
import com.example.LoginPage.OneTimePassword.OTPmodel.OTP;
import com.example.LoginPage.OneTimePassword.OtpRepository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OtpServiceImpl implements OtpService {
    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private OtpRepository otpRepository;
    @Value("${twilio.AccountSid}")
    private String authId;

    //    @Value("${plivo.auth.token}")
//    private String authToken;
    @Value("${twilio.AuthToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;
    @Value("${twilio.sendMessage}")
    private String sendMessage;
    @Autowired
    private UserRepository userRepository;

    public String sendSms(String to) {
        Twilio.init(authId, authToken);
        String otp=new String();
        try {
            otp=generateOtp();
            OTP obj =new OTP();
            //Generate encrypted data
            obj.setOtp(tokenManager.generateToken(Long.parseLong(otp)));
            obj.setExpiryTime(LocalDateTime.now());
            obj.setPhone(to);
            otpRepository.save(obj);
            Message message=Message.creator(new com.twilio.type.PhoneNumber(to), new com.twilio.type.PhoneNumber(phoneNumber), otp+" "+sendMessage).create();
//            MessageCreateResponse response = message.creator("8090564705", to, otp+sendMessage);
            // Check the status of the sent message
            if (message.getStatus() == Message.Status.FAILED) {
                // Twilio failed to send the message
                // Handle the failure accordingly, log it, throw an exception, etc.
                // For example, you might want to log the error:
                logger.error(StringAll.otpFailedatProvider+" "+ message.getErrorCode());
            }
        } catch (Exception ex) {
            logger.error(StringAll.otpFailedtoSend, ex);
        }
        return otp;
//        return response;
    }
    public static String generateOtp() {
        // Generate a 4-digit random OTP
        Random random = new Random();
        int otp = 1_000 + random.nextInt(9_000);
        return String.valueOf(otp);
    }
    public  boolean ValidateOtpService(String phone, String otp) throws Exception {
        //OTP if single time user inserts OTP;
        Optional<OTP> fetchedOtp = otpRepository.findByPhone(phone);
        OTP retrievedOtp= fetchedOtp.get();
        String s=tokenManager.generateToken(Long.parseLong(otp));
        LocalDateTime currentTime = LocalDateTime.now();
        /*
        Validations: 1. Check if fetchedOtp is null or invalid number is passed
                     2. Compare both the OTPs incoming and Database (valid or not)
                     3. Check if OTP is expired or not;
                     4. Latest OTP only be checked remaining will be invalid/Resend OTP as latest OTP
        */
        if (retrievedOtp.getPhone()!=null && retrievedOtp.getOtp().equals(s) && ChronoUnit.MINUTES.between(retrievedOtp.getExpiryTime(), currentTime)<=5) {
            return true;
        }
        return false;

    }
    public GlobalResponse validateOtp(@RequestBody OtpValidation otpValidation) throws Exception {
//        boolean otpEntityOptional = plivoService.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
//        boolean otpEntityOptional=true;
        GlobalResponse.VerifyResponse verifyResponse=new GlobalResponse.VerifyResponse();
        GlobalResponse globalResponse=new GlobalResponse();
        TokenManager tokenManager=new TokenManager();
        try {
            boolean otpEntityOptional =ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
            if (otpEntityOptional) {
                //Check if user Exists or not :
                User user = userRepository.findByPhone(otpValidation.getPhone());
                if (user.getLastState().equals(LastState.HAVEKIDS))
                {
                    verifyResponse.setAlreadyUser(false);
                    verifyResponse.setOnboarded(true);
                    String generatedToken = tokenManager.generateToken(user.getId());
                    verifyResponse.setToken(generatedToken);
                }
                else if (user != null) {
                    //it will be true if user Exists in database:
                    // Case 1 : Onboarding Completed
                    // Case 2: Still OnBoarding
                    //TODO page will get Opened
                    //Phone Number Mst Also Exist in Database
                    verifyResponse.setAlreadyUser(true);
                    String generatedToken = tokenManager.generateToken(user.getId());
                    verifyResponse.setToken(generatedToken);
                } else {
                    //User Landing First Time  save The user
                    User saveUser = new User();
                    saveUser.setPhone(otpValidation.getPhone());
                    User savedUser = userRepository.save(saveUser);
                    String generatedToken = tokenManager.generateToken(savedUser.getId());
                    verifyResponse.setToken(generatedToken);
                    verifyResponse.setAlreadyUser(false);
                    //Not required: Just for safety: ByDefault false
                    //Return A page where he left:
                }
                verifyResponse.setValidOtp(true);
                verifyResponse.setUser(user);
//                globalResponse.setMesaage("otp verified successfully");
                globalResponse.setMeassage(StringAll.otpVerified);
                globalResponse.setStatus(VerifyOtpEnum.SUCCESS);
                globalResponse.setVerifyResponse(verifyResponse);
                return globalResponse;
            } else {
                verifyResponse.setValidOtp(false);
                globalResponse.setMeassage(StringAll.otpVerificationFailed);
                globalResponse.setStatus(VerifyOtpEnum.FAILED);
//                verifyResponse.setMesaage("otp failed to verify");
                return globalResponse;
            }
        } catch (FailedToLoginException e) {
            throw new FailedToLoginException();
        } catch (Exception e) {
            // Handle other exceptions
            throw new Exception();
        }
    }
}
//    LocalDateTime currentTime = LocalDateTime.now();
//    Calculate the time difference in minutes
//    long minutesDifference = ChronoUnit.MINUTES.between(savedTime, currentTime);
//    return minutesDifference <= 5;