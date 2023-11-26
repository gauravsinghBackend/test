package com.example.LoginPage.DTO;

import com.example.LoginPage.Models.Parent_Role;
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
    private String date;
//    private String password;
//    private boolean isPasswordLess;
    private Parent_Role parentRole;
}
