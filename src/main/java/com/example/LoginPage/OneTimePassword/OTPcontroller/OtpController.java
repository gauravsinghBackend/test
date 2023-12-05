package com.example.LoginPage.OneTimePassword.OTPcontroller;
import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
import com.example.LoginPage.OneTimePassword.DTO.SmsRequest;
import com.example.LoginPage.OneTimePassword.OTPservice.OtpRepository;
import com.example.LoginPage.OneTimePassword.OTPservice.OtpServiceImpl;
import com.example.LoginPage.LoginSignUp.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class OtpController {

    private OtpService otpService;
    private UserRepository userRepository;

    private OtpRepository otpRepository;

    @Autowired
    public OtpController(OtpService otpService, UserRepository userRepository) {
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> sendSms(SmsRequest smsRequest) {
        try {
            String otp = otpService.sendSms(smsRequest.getPhone());
            return new ResponseEntity<>(StringAll.otpSendSuccess, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(StringAll.otpFailedtoSend + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<GlobalResponse> validateOtp(@RequestBody OtpValidation otpValidation) throws Exception {
        try {
            GlobalResponse globalResponse = otpService.validateOtp(otpValidation);
            return new ResponseEntity<>(globalResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}



