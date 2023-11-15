package com.example.LoginPage.PasswordReset.Repository;

import com.example.LoginPage.PasswordReset.Service.PasswordResetTokenDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenDto, String> {
    Optional<PasswordResetTokenDto> findByToken(String token);
}
