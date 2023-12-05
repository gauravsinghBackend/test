package com.example.LoginPage.LoginSignUp;

import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.LoginSignUp.DTO.SignUpResponseDto;
import com.example.LoginPage.LoginSignUp.DTO.SignUpUpdate;
import com.example.LoginPage.LoginSignUp.DTO.SignupDto;
import com.example.LoginPage.LoginSignUp.DTO.UserDto;
import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import com.example.LoginPage.OneTimePassword.DTO.OtpStatus;
import com.example.LoginPage.OneTimePassword.OTPcontroller.OtpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
//@RequestMapping("/v1.0/users")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpController plivoController;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @PostMapping("/login")
    public ResponseEntity<OtpResponse> login(@RequestBody UserDto userDto) {
        try {
            ResponseEntity<OtpResponse> otpResponse= loginService.authenticateUser(userDto.getPhone());
            logger.error(StringAll.otpSendSuccess);
            return otpResponse;
        }
        catch (Exception e) {
            logger.error(StringAll.otpFailedtoSend, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OtpResponse(OtpStatus.FAILED,StringAll.otpFailedtoSend));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> registration(@RequestHeader("Authorization") String header, @RequestBody SignupDto signupDto) throws Exception {
        //Check The user from Header String
        //Extra method in token Manager needs to be made to Do these tasks
        SignUpResponseDto signUpResponseDto=new SignUpResponseDto();
        try {
            signUpResponseDto=loginService.saveUser(header,signupDto);
            logger.error(StringAll.signedSuccessfully);
            return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error in signing up", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignUpResponseDto(StringAll.failedSignIn,SignUpUpdate.FAILED));
        }
    }
    @GetMapping("/user")
    public ResponseEntity<String > getUser(){

        return new ResponseEntity<>("UserExists",HttpStatus.OK);
    }
    @GetMapping("/test-controller")
    public ResponseEntity<String> testController(){
        return new ResponseEntity<>("test-controller reached",HttpStatus.OK);
    }

}
