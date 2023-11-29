package com.example.LoginPage.LoginSignUp;

import com.example.LoginPage.Models.UserAuthentificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<UserAuthentificationToken,Long> {
    UserAuthentificationToken findByToken(String token);
}
