package com.example.LoginPage.OneTimePassword.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse {
    private OtpStatus status;
    private String message;
}
