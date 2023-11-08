package com.example.LoginPage.DTO;

import lombok.Data;

@Data
public class SignupDto {
//    PayLoad
//    name: string,
//    phone: string,
//    email: string,
//    password: string,
//    isPasswordLess: boolean
    private String name;
    private String phone;
    private String email;
    private String password;
    private boolean isPasswordLess;
}
