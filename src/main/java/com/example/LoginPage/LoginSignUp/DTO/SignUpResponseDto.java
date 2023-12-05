package com.example.LoginPage.LoginSignUp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private String meassage;
    private SignUpUpdate status;
}
