package com.example.LoginPage.OneTimePassword.OTPcontroller;

import com.example.LoginPage.Models.User;
import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.OTPservice.PlivoService;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyOTP {
    @Autowired
    private PlivoService plivoService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify")
    public ResponseEntity<VerifyResponse> validateOtp(@RequestBody OtpValidation otpValidation) {
        boolean otpEntityOptional = plivoService.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
//        boolean otpEntityOptional=true;
        VerifyResponse verifyResponse=new VerifyResponse();
        if (otpEntityOptional) {
            //Check if user Exists or not :
            User user= userRepository.findByPhone(otpValidation.getPhone());
            if (user!=null) {//it will be true if user Exists in database:
                //TODO page will get Opened
                verifyResponse.setAlreadyUser(true);
            }
            else{
                verifyResponse.setAlreadyUser(false); //Not required: Just for safety: ByDefault false
                //Return A page where he left:

            }
            //For testing
//            User user=new User();
//            user.setName("gaurav");
//            user.setEmail("gaurav@zevo360");
            verifyResponse.setValidOtp(true);
            verifyResponse.setUser(user);
            return ResponseEntity.ok(verifyResponse);
        } else {
            verifyResponse.setValidOtp(false);
            return ResponseEntity.badRequest().body(verifyResponse);
        }
    }
}
