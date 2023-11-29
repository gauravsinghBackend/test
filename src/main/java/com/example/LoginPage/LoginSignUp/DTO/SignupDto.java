package com.example.LoginPage.LoginSignUp.DTO;

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
    //Phone Is already Saved in DB
//    private String phone;
    private String email;
    private String date;
//    private String password;
//    private boolean isPasswordLess;
    private String parentrole;
}
