package com.example.LoginPage.PasswordReset.Service;

import com.example.LoginPage.Models.User;
import com.example.LoginPage.PasswordReset.Repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    public PasswordResetTokenDto createToken(User user) {
        PasswordResetTokenDto passwordResetToken = TokenGeneration(user);
        tokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    public Optional<PasswordResetTokenDto> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

//    public void deleteToken(String token) {
//        tokenRepository.deleteById(token);
//    }
    public PasswordResetTokenDto TokenGeneration(User user) {
        PasswordResetTokenDto passwordResetTokenDto = new PasswordResetTokenDto();
        passwordResetTokenDto.setToken(UUID.randomUUID().toString());
        passwordResetTokenDto.setUser(user);
        passwordResetTokenDto.setExpiryDate(LocalDateTime.now().plusMinutes(5)); // Set to expire in 5 minutes
        return passwordResetTokenDto;
    }
    public String generateOtp() {
        // Generate a 4-digit random OTP
        Random random = new Random();
        int otp = 1_000 + random.nextInt(9_000);
        return String.valueOf(otp);
    }
    public void SendEmail(String to, String body, String subject){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }
//    @EventListener(ApplicationContext.class)
//    public void MailInitiate(){
//        SendEmail();
//    }
}

