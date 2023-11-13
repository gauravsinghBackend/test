package com.example.LoginPage.OneTimePassword.OTPservice;
import com.plivo.api.Plivo;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import com.plivo.api.models.message.MessageCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PlivoService {

    @Autowired
    private Plivo plivo;

    @Value("${plivo.phone.number}")
    private String plivoPhoneNumber;

    public String sendSms(String to) {
        String otp=new String();
        try {
            otp=generateOtp();
//            MessageCreateResponse response = Message.creator(to, plivoPhoneNumber, "OTP send successfully");
        } catch (Exception ex) {
            System.out.println("sorry");
        }
        return otp;
    }
    public static String generateOtp() {
        // Generate a 4-digit random OTP
        Random random = new Random();
        int otp = 1_000 + random.nextInt(9_000);
        return String.valueOf(otp);
    }
}


