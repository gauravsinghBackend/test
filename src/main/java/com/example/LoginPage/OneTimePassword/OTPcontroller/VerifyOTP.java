package com.example.LoginPage.OneTimePassword.OTPcontroller;

import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.OTPservice.PlivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyOTP {
    @Autowired
    private PlivoService plivoService;

    @PostMapping("/verify")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidation otpValidation) {
        boolean otpEntityOptional = plivoService.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
        if (otpEntityOptional) {

            return ResponseEntity.ok("OTP verified successfully.");
        } else {

            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }
}
