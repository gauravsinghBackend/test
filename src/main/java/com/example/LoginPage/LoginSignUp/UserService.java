package com.example.LoginPage.LoginSignUp;



import com.example.LoginPage.Models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserService {
    void saveUser();
    Optional<User> findUserByEmail(String email);
}