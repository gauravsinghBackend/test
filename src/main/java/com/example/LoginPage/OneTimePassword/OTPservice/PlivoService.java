package com.example.LoginPage.OneTimePassword.OTPservice;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.OneTimePassword.OTPmodel.OTP;
import com.example.LoginPage.OneTimePassword.OtpRepository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlivoService {
    private static final Logger logger = LoggerFactory.getLogger(PlivoService.class);
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

    public String sendSms(String to) {
//    public MessageCreateResponse sendSms(String to) {
        // Initialize the Twilio client
        Twilio.init(authId, authToken);
        String otp=new String();
        try {
//            Plivo.init("MAYZHJOTLHODQXMZM3NM","ZjYxZTE3MTc5NzZkMmE4MmU4OGQ0NGRiODkwZjhj");
            otp=generateOtp();
            OTP obj =new OTP();
//            obj.setOtp(otp);
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
                logger.error("failed to send message. Error code: {}", message.getErrorCode());
            }
        } catch (Exception ex) {
            logger.error("error sending message", ex);
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
}
//    LocalDateTime currentTime = LocalDateTime.now();
//    Calculate the time difference in minutes
//    long minutesDifference = ChronoUnit.MINUTES.between(savedTime, currentTime);
//    return minutesDifference <= 5;