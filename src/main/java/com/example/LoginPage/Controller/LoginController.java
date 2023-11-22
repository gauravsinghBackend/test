package com.example.LoginPage.Controller;

import com.example.LoginPage.DTO.ForgetPasswordDto;
import com.example.LoginPage.DTO.SignupDto;
import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.UserRepository;
import com.example.LoginPage.Service.UserService;
import com.example.LoginPage.Service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1.0/users")
public class LoginController {
    @Autowired
    private UserServiceImpl userServiceImpl;
//    @Autowired
//    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        // Check if the user with the given email and password exists in the database
//        User existingUser = userServiceImpl.authenticateUser(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        User existingUser = userServiceImpl.authenticateUser(userDto.getEmail(), userDto.getPassword());

        if (existingUser == null) {
            // Authentication failed, return an error response
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        } else {
            // Authentication succeeded, return a success response
            return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registration(@RequestBody SignupDto signupDto) {
        // Check if the user with the given email already exists in the database
        Optional<User> existingUser = userRepository.findByEmail(signupDto.getEmail());

        if (existingUser.isPresent()) {
            // User with the same email already exists
            return new ResponseEntity<>("User Already Exists", HttpStatus.FOUND);
        } else {
            // User does not exist, so save the new user to the database
    //        User user = new User();
    //        user.setName(signupDto.getName());
    //        user.setEmail(signupDto.getEmail());
    //        user.setPassword(signupDto.getPassword());
    //        user.setPhone(signupDto.getPhone());
            userServiceImpl.saveUser(signupDto);
            // Return a success response
            return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
        }

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
