package com.example.LoginPage.Service;


import com.example.LoginPage.DTO.SignupDto;
import com.example.LoginPage.DTO.UserDto;
import com.example.LoginPage.Models.Role;
import com.example.LoginPage.Models.TbConstants;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.RoleRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(SignupDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword())); // password encoder will be used here
//        user.setPassword(userDto.getPassword());
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
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + username);
//        }

        // You should convert your User entity to UserDetails (e.g., org.springframework.security.core.userdetails.User).
        // Ensure that you provide the user's username (email), password, and authorities (roles).
//        user.setName(username);
//        return user;
//                User.builder()
//                .username(user.getEmail()) // This should be the email
//                .password(user.getPassword()) // The user's password (make sure it's hashed)
//                .authorities(user.getRoles()) // Set the user's roles/authorities
//                .build();
//    }
}
