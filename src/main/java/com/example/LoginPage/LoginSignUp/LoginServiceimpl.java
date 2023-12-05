package com.example.LoginPage.LoginSignUp;


import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.DTO.SignUpResponseDto;
import com.example.LoginPage.LoginSignUp.DTO.SignUpUpdate;
import com.example.LoginPage.LoginSignUp.DTO.SignupDto;
import com.example.LoginPage.Models.ParentRole;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OnBoarding.LastState;
import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import com.example.LoginPage.OneTimePassword.DTO.OtpStatus;
import com.example.LoginPage.OneTimePassword.DTO.SmsRequest;
import com.example.LoginPage.OneTimePassword.OTPcontroller.OtpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OtpController plivoController;
    @Autowired
    private TokenManager tokenManager;

    private final int bearerSubstring=7;

    public ResponseEntity<OtpResponse> authenticateUser(String phone) {
        try {
            User user = userRepository.findByPhone(phone);
            SmsRequest smsRequest1 = new SmsRequest();
            smsRequest1.setPhone(phone);
            plivoController.sendSms(smsRequest1);
            OtpResponse otpStatus = new OtpResponse();
            otpStatus.setStatus(OtpStatus.SUCCESS);
            otpStatus.setMessage(StringAll.otpSendSuccess);

            return new ResponseEntity<>(otpStatus, HttpStatus.OK);
        } catch (Exception e) {
            OtpResponse otpStatus = new OtpResponse();
            otpStatus.setMessage(StringAll.otpFailedtoSend);
            otpStatus.setStatus(OtpStatus.FAILED);
            return new ResponseEntity<>(otpStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SignUpResponseDto saveUser(String header, SignupDto signupDto) throws Exception {
        SignUpResponseDto signUpResponseDto=new SignUpResponseDto();
        if (header == null) {
            signUpResponseDto.setMeassage(StringAll.headerMissing);
            signUpResponseDto.setStatus(SignUpUpdate.FAILED);
            return signUpResponseDto;
        }
        String token = header.substring(bearerSubstring);
        TokenData tokenData = tokenManager.decryptToken(token);
        if (tokenData == null) {
            signUpResponseDto.setMeassage(StringAll.headerIncorrect);
            signUpResponseDto.setStatus(SignUpUpdate.FAILED);
            return signUpResponseDto;
        }
        Optional<User> user = userRepository.findById(tokenData.getUserId());
        //validate if user is null
        if (user.isEmpty()) {
            signUpResponseDto.setMeassage(StringAll.invalidUser);
            signUpResponseDto.setStatus(SignUpUpdate.FAILED);
            return signUpResponseDto;
        }
        // Return a success response (Use Builder)
        User user1 = user.get();
        user1.setName(signupDto.getName());
        user1.setDate(signupDto.getDate());
        user1.setEmail(signupDto.getEmail());
        user1.setParentrole(ParentRole.fromString(signupDto.getParentrole()));
        user1.setLastState(LastState.SIGNUP);
        //Do we need to handle the case where may repository Backfire
        userRepository.save(user1);
        signUpResponseDto.setMeassage(StringAll.signInSuccess);
        signUpResponseDto.setStatus(SignUpUpdate.SUCCESS);
        return signUpResponseDto;
    }
}
