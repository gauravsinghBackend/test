package com.example.LoginPage.Service;



import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserService {
    void saveUser();
    Optional<User> findUserByEmail(String email);
}