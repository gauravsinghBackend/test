package com.example.LoginPage.PasswordReset.Controller;

import com.example.LoginPage.Models.User;
import com.example.LoginPage.PasswordReset.DTO.ResetPasswordRequestDto;
import com.example.LoginPage.PasswordReset.Service.PasswordResetTokenDto;
import com.example.LoginPage.PasswordReset.Service.PasswordResetTokenService;
import com.example.LoginPage.Repository.UserRepository;
import com.example.LoginPage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private EmailService emailService;
    @PostMapping("/forgot-password")
    public ResponseEntity<String> processForgotPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        Optional<User> user = userRepository.findByEmail(resetPasswordRequestDto.getEmail());
        if (user.isPresent()) {
            // User found, generate a reset token+User+Expiry Date
            passwordResetTokenService.SendEmail(resetPasswordRequestDto.getEmail(),"DeepLink","OTP reset Link");
            PasswordResetTokenDto token = passwordResetTokenService.createToken(user.get());
            String OTP =passwordResetTokenService.generateOtp();
            return new ResponseEntity<>("OTP for the email is"+OTP,HttpStatus.OK);
            // Send email with the reset link
//            emailService.sendPasswordResetEmail(email, token.getToken());

//            model.addAttribute("message", "Password reset link sent to your email.");
        } else {
            // User not found (or you may want to handle this more gracefully)
//            model.addAttribute("error", "Email not found.");
            return new ResponseEntity<>("User not found witl email ID "+resetPasswordRequestDto.getEmail(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Find the token in the database
        Optional<PasswordResetTokenDto> passwordResetToken = passwordResetTokenService.findByToken(token);

        if (passwordResetToken.isPresent() && passwordResetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            // Token is valid, proceed with password reset
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            // Token is invalid or expired
            model.addAttribute("error", "Invalid or expired token.");
            return "error";
        }
    }
//
//    @PostMapping("/reset-password")
//    public String processResetPassword(@RequestParam("token") String token,
//                                       @RequestParam("password") String newPassword,
//                                       Model model) {
//        Optional<PasswordResetTokenDto> passwordResetToken = tokenService.findByToken(token);
//
//        if (passwordResetToken.isPresent() && passwordResetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
//            // Token is valid, proceed with password reset
//            User user = passwordResetToken.get().getUser();
//            user.setPassword(newPassword);
//            userService.saveUser(user);
//
//            // Delete the used token
//            tokenService.deleteToken(token);
//
//            model.addAttribute("message", "Password reset successfully.");
//        } else {
//            // Token is invalid or expired
//            model.addAttribute("error", "Invalid or expired token.");
//        }
//
//        return "reset-password";
//    }
}
