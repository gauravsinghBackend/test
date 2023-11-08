package com.example.LoginPage.Service;



import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.User;

import java.util.List;

public interface UserService {
    void saveUser();

    User findUserByEmail(String email);
}