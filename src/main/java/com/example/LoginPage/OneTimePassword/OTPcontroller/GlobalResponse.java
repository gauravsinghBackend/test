package com.example.LoginPage.OneTimePassword.OTPcontroller;

import com.example.LoginPage.Models.User;
import lombok.Data;

@Data
public class GlobalResponse {
    private String meassage;
    private VerifyOtpEnum status;
    private VerifyResponse verifyResponse;
    @Data
    public static class VerifyResponse {
        private boolean isValidOtp;
        private boolean alreadyUser;
        private boolean isOnboarded;
        private User user;
        private String token;
//        private String mesaage;
//        private VerifyResponse verifyResponse;
    }
}
