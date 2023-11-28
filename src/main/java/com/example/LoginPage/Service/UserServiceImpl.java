package com.example.LoginPage.Service;


import com.example.LoginPage.DTO.SignupDto;
import com.example.LoginPage.Models.ParentRole;
import com.example.LoginPage.Models.Role;
import com.example.LoginPage.Models.TbConstants;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OnBoarding.LastState;
import com.example.LoginPage.Repository.RoleRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(Optional<User>user ,SignupDto signupDto) {
//        Role role = roleRepository.findByName(TbConstants.Roles.USER);
//
//        if (role == null)
//        role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user1=user.get();
        user1.setName(signupDto.getName());
        user1.setDate(signupDto.getDate());
        user1.setEmail(signupDto.getEmail());
        //Phone is set Already as per new Plan
//        user.setPhone(userDto.getPhone());
//        user.setDate(pa);
//        user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword())); // password encoder will be used here
//        user.setPassword(userDto.getPassword());
//        user1.setRoles(Arrays.asList(role));
        user1.setParentrole(ParentRole.fromString(signupDto.getParentrole()));
        user1.setLastState(LastState.SIGNUP);
        userRepository.save(user1);
    }
//    public User authenticateUser(String email, String password) {
public User authenticateUser(String phone) {
//        Optional<User> user = userRepository.findByEmail(email);
    User user = userRepository.findByPhone(phone);
//        User AuthenticatedUser =user.get();
        //Here we need to convert incoming password in requestBody to Bcrypt than compare it.
//        if (user != null && AuthenticatedUser.getPassword().equals(password)) {
//            return AuthenticatedUser; // Authentication successful
//        } else {
//            return null; // Authentication failed
//        }

//    return fetchedUser;
    return user;
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
