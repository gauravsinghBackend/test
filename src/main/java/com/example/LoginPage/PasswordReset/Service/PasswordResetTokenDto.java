package com.example.LoginPage.PasswordReset.Service;

import com.example.LoginPage.Models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class PasswordResetTokenDto {

    @Id
    private String token;
    @ManyToOne
    private User user;
    private LocalDateTime expiryDate;
    // Method to create a new token
}
