package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.UserRepository;
import com.example.LoginPage.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KidServiceImpl implements KidService{
    @Autowired
    private KidRepository kidRepository;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserRepository userRepository;

    public KidResponseDto addKid(String header, KidRequestDto kidRequestDto) throws Exception {
        KidResponseDto kidResponseDto=new KidResponseDto();
        //Validate if Header is null
        if (header==null) {
            kidResponseDto.setMessage("header missing");
            return kidResponseDto;
        }
        String token = header.substring(7);
        TokenData tokenData = tokenManager.decryptToken(token);
        if (tokenData==null)
        {
            kidResponseDto.setMessage("incorrect header");
            return kidResponseDto;
        }
        Optional<User> user = userRepository.findById(tokenData.getUserId());
        //validate if user is null
        if (user.isEmpty()) {
            kidResponseDto.setMessage("user is Invalid or null");
            return kidResponseDto;
        }
        Kid kid=new Kid();
        kid.setName(kidRequestDto.getName());
        kid.setDob(kidRequestDto.getDob());
        kid.setProfilePicture(kidRequestDto.getProfilePicture());
        kid.setUser(user.get());
        KidGender genderEnum = KidGender.fromString(kidRequestDto.getGender());
        kid.setKidGender(genderEnum);
        Kid savedKid=kidRepository.save(kid);
        kidResponseDto.setId(kid.getId());
        kidResponseDto.setMessage("kid saved successfully");
        return kidResponseDto;
    }
}
