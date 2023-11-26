package com.example.LoginPage.OneTimePassword.DTO;

import lombok.Data;

@Data
public class OtpResponse {
    private OtpStatus otpStatus;
    private String message;
}
