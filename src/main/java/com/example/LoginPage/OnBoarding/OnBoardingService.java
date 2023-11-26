package com.example.LoginPage.OnBoarding;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OnBoardingService {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<PregnantChildResponseDto> PregnantorChild(String header, PregnantChildRequestDto pregnantChildRequestDto) throws Exception {
        Optional<User> user=retrieveUserid(header);
        PregnantChildResponseDto pregnantChildResponseDto=new PregnantChildResponseDto();
            if (user.isEmpty()){
            pregnantChildResponseDto.setMessage("User not found");
            return new ResponseEntity<>(pregnantChildResponseDto, HttpStatus.OK);
        }
        User updateUser=user.get();
            updateUser.setPregnantorchild(PregnantChildEnum.fromPregnantChild(pregnantChildRequestDto.isWeArePregnant()));
            userRepository.save(updateUser);
            pregnantChildResponseDto.setMessage("parentorchild updated successfully");
            return new ResponseEntity<>(pregnantChildResponseDto,HttpStatus.OK);
    }
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
