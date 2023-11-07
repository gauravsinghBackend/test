package com.example.LoginPage.Service;


import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.Role;
import com.example.LoginPage.Models.TbConstants;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.RoleRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role));
        userRepository.save(user);
    }
    public User authenticateUser(String email, String password) {
        // Implement your logic to authenticate the user here
        // You can use your UserRepository or any other method to check the user's credentials
        // For simplicity, we'll assume you have a UserRepository.

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user; // Authentication successful
        } else {
            return null; // Authentication failed
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
