package com.example.LoginPage.OneTimePassword.OTPmodel;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String otp;
    private LocalDateTime expiryTime;
}
