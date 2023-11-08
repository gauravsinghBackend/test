package com.example.LoginPage.Controller;

import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Service.UserService;
import com.example.LoginPage.Service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private UserServiceImpl userServiceImpl;
//    @Autowired
//    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        // Check if the user with the given email and password exists in the database
        User existingUser = userServiceImpl.authenticateUser(userDto.getEmail(), userDto.getPassword());

        if (existingUser == null) {
            // Authentication failed, return an error response
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        } else {
            // Authentication succeeded, return a success response
            return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
        }
    }

//    @GetMapping("/registration")
//    public String registrationForm(Model model) {
//        UserDto user = new UserDto();
//        model.addAttribute("user", user);
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registration(
//            @Valid @ModelAttribute("user") UserDto userDto,
//            BindingResult result,
//            Model model) {
//        User existingUser = userService.findUserByEmail(userDto.getEmail());
//
//        if (existingUser != null)
//            result.rejectValue("email", null,
//                    "User already registered !!!");
//
//        if (result.hasErrors()) {
//            model.addAttribute("user", userDto);
//            return "/registration";
//        }
//
//        userService.saveUser(userDto);
//        return "redirect:/registration?success";
//    }
}
