package com.example.LoginPage.LoginSignUp;

import com.example.LoginPage.LoginSignUp.DTO.SignUpResponseDto;
import com.example.LoginPage.LoginSignUp.DTO.SignupDto;
import com.example.LoginPage.LoginSignUp.DTO.UserDto;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface LoginService {
//    ResponseEntity<OtpResponse> login(UserDto userDto);

    ResponseEntity<OtpResponse> authenticateUser(String phone);

    SignUpResponseDto saveUser(String header, SignupDto signupDto) throws Exception;
}
