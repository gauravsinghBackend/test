//package com.example.LoginPage.PasswordReset.Service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void sendPasswordResetEmail(String toEmail, String token) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Password Reset");
//        message.setText("To reset your password, click the link: http://your-app/reset-password?token=" + token);
//        mailSender.send(message);
//    }
//}

//This class will be used if we are sending mails