package com.example.LoginPage.OneTimePassword.OTPcontroller;

import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.FailedToLoginException;
import org.springframework.http.ResponseEntity;

public interface OtpService {
    public String sendSms(String to);
    public GlobalResponse validateOtp(OtpValidation otpValidation) throws Exception;
}
