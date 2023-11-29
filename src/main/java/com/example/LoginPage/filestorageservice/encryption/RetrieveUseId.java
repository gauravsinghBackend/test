package com.example.LoginPage.filestorageservice.encryption;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.UserRepository;
import com.example.LoginPage.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class RetrieveUseId {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;
    public Optional<User> retrieveUserid(String header) throws Exception {
        //Validate if Header is null
        if (header==null) {
            return null;
        }
        String token = header.substring(7);
        TokenData tokenData = tokenManager.decryptToken(token);
        //Decrypted Token Has No value
        if (tokenData==null) {
            return null;
        }
        Optional<User> user = userRepository.findById(tokenData.getUserId());
        //validate if user is null(user not found in Database
        if (user.isEmpty()) {
            return null;
        }
        //return the extracted data from Database
        return user;
    }
}
