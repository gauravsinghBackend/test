package com.example.LoginPage.OneTimePassword.OTPcontroller;

import com.example.LoginPage.Models.User;
import lombok.Data;

@Data
public class VerifyResponse {
    private boolean isValidOtp;
    private boolean alreadyUser;
    private boolean isOnboarded;
    private User user;
    private String token;
}
