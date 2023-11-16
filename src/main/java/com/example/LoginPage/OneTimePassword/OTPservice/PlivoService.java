package com.example.LoginPage.OneTimePassword.OTPservice;
import com.example.LoginPage.OneTimePassword.OTPmodel.OTP;
import com.example.LoginPage.OneTimePassword.OtpRepository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class PlivoService {

//    @Autowired
//    private Plivo plivo;
//    @Autowired
//    private Message message;

    @Value("${plivo.phone.number}")
    private String plivoPhoneNumber;
    @Autowired
    private OtpRepository otpRepository;

    public String sendSms(String to) {
//    public MessageCreateResponse sendSms(String to) {
        String otp=new String();
        try {
//            Plivo.init("MAYZHJOTLHODQXMZM3NM","ZjYxZTE3MTc5NzZkMmE4MmU4OGQ0NGRiODkwZjhj");
            otp=generateOtp();
            OTP obj =new OTP();
            obj.setOtp(otp);
            obj.setExpiryTime(LocalDateTime.now());
            obj.setPhone(to);
            otpRepository.save(obj);

//            MessageCreateResponse response = message.creator("8090564705", to, "OTP send successfully"+otp);
        } catch (Exception ex) {
            System.out.println("sorry");
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
    //This method will be hit by plivo to see that sms delivered or not:
//    @PostMapping("/sms_status")
//    public ResponseEntity<String> handleSmsStatus(@RequestBody String smsStatus) {
//        // Handle the SMS delivery status
//        System.out.println("Received SMS Status: " + smsStatus);
//        // You can perform additional processing based on the received status
//        return ResponseEntity.ok("Received SMS Status");
//    }
    public  boolean ValidateOtpService(String phone, String otp){
        //OTP if single time user inserts OTP;
        Optional<OTP> fetchedOtp = otpRepository.findByPhone(phone);
        OTP retrievedOtp= fetchedOtp.get();
        LocalDateTime currentTime = LocalDateTime.now();
        /*
        Validations: 1. Check if fetchedOtp is null or invalid number is passed
                     2. Compare both the OTPs incoming and Database (valid or not)
                     3. Check if OTP is expired or not;
                     4. Latest OTP only be checked remaining will be invalid/Resend OTP as latest OTP
        */
        if (retrievedOtp.getPhone()!=null && retrievedOtp.getOtp().equals(otp) && ChronoUnit.MINUTES.between(retrievedOtp.getExpiryTime(), currentTime)<=5) {
            return true;
        }
        return false;

    }
}
//    LocalDateTime currentTime = LocalDateTime.now();
//    Calculate the time difference in minutes
//    long minutesDifference = ChronoUnit.MINUTES.between(savedTime, currentTime);
//    return minutesDifference <= 5;