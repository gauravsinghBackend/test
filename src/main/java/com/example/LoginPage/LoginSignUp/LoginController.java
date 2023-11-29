package com.example.LoginPage.LoginSignUp;

import com.example.LoginPage.LoginSignUp.DTO.SignUpResponseDto;
import com.example.LoginPage.LoginSignUp.DTO.SignupDto;
import com.example.LoginPage.LoginSignUp.DTO.UserDto;
import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import com.example.LoginPage.OneTimePassword.DTO.OtpStatus;
import com.example.LoginPage.OneTimePassword.DTO.SmsRequest;
import com.example.LoginPage.OneTimePassword.OTPcontroller.PlivoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
//@RequestMapping("/v1.0/users")
public class LoginController {
    @Autowired
    private UserServiceImpl userServiceImpl;
//    @Autowired
//    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private PlivoController plivoController;


    @PostMapping("/login")
    public ResponseEntity<OtpResponse> login(@RequestBody UserDto userDto) {
//    public ResponseEntity<String> login(@RequestParam ("phone") String phone) throws Exception {
        // Check if the user with the given email and password exists in the database
//        User existingUser = userServiceImpl.authenticateUser(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
//        User existingUser = userServiceImpl.authenticateUser(userDto.getEmail(), userDto.getPassword());
        //NEW LOGIC for OTP sending
        User existingUser = userServiceImpl.authenticateUser(userDto.getPhone());
        SmsRequest smsRequest1=new SmsRequest();
        smsRequest1.setPhone(userDto.getPhone());
//        String s=phone;
//        if (s==null)
//        {
//            return new ResponseEntity<>("Phone Not found",HttpStatus.OK);
//        }
        plivoController.sendSms(smsRequest1);
        OtpResponse otpStatus=new OtpResponse();
        otpStatus.setOtpStatus(OtpStatus.SUCCESS);
        otpStatus.setMessage("otp send successfully");

//        if (existingUser == null) {

            // Authentication failed, return an error response
            return new ResponseEntity<>(otpStatus, HttpStatus.OK);
//        } else {
            // Authentication succeeded, return a success response
//            String token=tokenManager.generateToken(existingUser.getId());
//            return new ResponseEntity<>(otpStatus, HttpStatus.OK);
//        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> registration(@RequestHeader("Authorization") String header, @RequestBody SignupDto signupDto) throws Exception {
        //Check The user from Header String
        //Extra method in token Manager needs to be made to Do these tasks
        SignUpResponseDto signUpResponseDto=new SignUpResponseDto();
        if (header==null) {
            signUpResponseDto.setMeassage("Header Missing");
            return new ResponseEntity<>(signUpResponseDto,HttpStatus.OK);
        }
        String token = header.substring(7);
        TokenData tokenData = tokenManager.decryptToken(token);
        if (tokenData==null)
        {
            signUpResponseDto.setMeassage("Incorrect Header");
            return new ResponseEntity<>(signUpResponseDto,HttpStatus.OK);
        }
        Optional<User> user = userRepository.findById(tokenData.getUserId());
        //validate if user is null
        if (user.isEmpty()) {
            signUpResponseDto.setMeassage("User is Invalid or Null");
            return new ResponseEntity<>(signUpResponseDto,HttpStatus.OK);
        }

        // Check if the user with the given email already exists in the database
//        Optional<User> existingUser = userRepository.findById(user.get().getId());
            // User does not exist, so save the new user to the database
    //        User user = new User();
    //        user.setName(signupDto.getName());
    //        user.setEmail(signupDto.getEmail());
    //        user.setPassword(signupDto.getPassword());
    //        user.setPhone(signupDto.getPhone());
            userServiceImpl.saveUser(user, signupDto);
            // Return a success response
        signUpResponseDto.setMeassage("User signed in successfully");
        return new ResponseEntity<>(signUpResponseDto,HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<String > getUser(){
        return new ResponseEntity<>("UserExists",HttpStatus.OK);
    }

    @GetMapping("/test-controller")
    public ResponseEntity<String> testController(){
        return new ResponseEntity<>("Test-Controller reached",HttpStatus.OK);
    }

//    @PostMapping ("/forgot-password")
//    public ResponseEntity<String> CheckEmail(@RequestBody ForgetPasswordDto forgetPasswordDto)
//    {
//        forgetPasswordDto.checkPassword(forgetPasswordDto)
//    }
}
