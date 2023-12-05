package com.example.LoginPage.OneTimePassword.OTPservice;

public interface OtpRepository {
    String sendSms(String phone);
}
