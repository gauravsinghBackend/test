package com.example.LoginPage.Service;


import com.example.LoginPage.DTO.SignupDto;
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
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public void saveUser(SignupDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // password encoder will be used here
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        //Here we need to convert incoming password in requestBody to Bcrypt than compare it.
        if (user != null && user.getPassword().equals(password)) {
            return user; // Authentication successful
        } else {
            return null; // Authentication failed
        }
    }

//    @Override
//    public User findUserByEmail(String email) {
//
//        return userRepository.findByEmail(email);
//    }
}
