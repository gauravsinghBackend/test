package com.example.LoginPage.OneTimePassword.OTPcontroller;
import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.DTO.SmsRequest;
import com.example.LoginPage.OneTimePassword.OTPmodel.OTP;
import com.example.LoginPage.OneTimePassword.OTPservice.PlivoService;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OneTimePassword.OtpRepository.OtpRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PlivoController {

    private PlivoService plivoService;
    private UserRepository userRepository;

    @Autowired
    public PlivoController(PlivoService plivoService, UserRepository userRepository) {
        this.plivoService = plivoService;
        this.userRepository = userRepository;
    }
//    @PostMapping("/otp")
//    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest) {
public ResponseEntity<String> sendSms(SmsRequest smsRequest) {
        User user = userRepository.findByPhone(smsRequest.getPhone());
        try {
            //It will get Changed for NEW VERSION of Figma
//            if (user != null) {
                String otp = plivoService.sendSms(smsRequest.getPhone());
                return new ResponseEntity<>("OTP generated successfully! " + otp, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("User not found for phone: " + smsRequest.getPhone(), HttpStatus.NOT_FOUND);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error generating OTP: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/verify")
//    public ResponseEntity<String> validateOtp(@RequestBody OtpValidation otpValidation) {
//        boolean otpEntityOptional = plivoService.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
//        if (otpEntityOptional) {
//            return ResponseEntity.ok("OTP verified successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid OTP.");
//        }
//    }
}



