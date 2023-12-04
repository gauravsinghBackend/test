package com.example.LoginPage.OnBoarding;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.LoginSignUp.UserRepository;
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
            //User SignedUp and set to Pregorchild aas 1;
            updateUser.setLastState(LastState.PREGORCHILD);
            userRepository.save(updateUser);
            pregnantChildResponseDto.setMessage("parentorchild updated successfully");
            return new ResponseEntity<>(pregnantChildResponseDto,HttpStatus.OK);
    }
    public ResponseEntity<PregnantTrueResponseDto> DueDate(String header, PregnantTrueRequestDto pregnantTrueRequestDto) throws Exception {
        Optional<User> user=retrieveUserid(header);
        PregnantTrueResponseDto pregnantChildResponseDto=new PregnantTrueResponseDto();
        if (user.isEmpty()){
            pregnantChildResponseDto.setMessage("User not found");
            return new ResponseEntity<>(pregnantChildResponseDto, HttpStatus.OK);
        }
        User updateUser=user.get();
        updateUser.setDueDate(pregnantTrueRequestDto.getDuedate());
        updateUser.setLastState(LastState.DUEDATE);
        userRepository.save(updateUser);
        pregnantChildResponseDto.setMessage("pregnantTrue updated successfully");
        return new ResponseEntity<>(pregnantChildResponseDto,HttpStatus.OK);
    }
    public ResponseEntity<FirstPregnancyResponseDto> FirstPregnancy(String header, FirstPregnancyRequestDto firstPregnancyRequestDto) throws Exception {
        Optional<User> user=retrieveUserid(header);
        FirstPregnancyResponseDto firstPregnancyResponseDto=new FirstPregnancyResponseDto();
        if (user.isEmpty()){
            firstPregnancyResponseDto.setMessage("user not found");
            return new ResponseEntity<>(firstPregnancyResponseDto, HttpStatus.OK);
        }
        User updateUser=user.get();
        updateUser.setFirstPregnancy(firstPregnancyRequestDto.isFirstpregnancy());
        updateUser.setLastState(LastState.FIRSTPREGNANCY);
        userRepository.save(updateUser);
        firstPregnancyResponseDto.setMessage("firstPregnancy updated successfully");
        return new ResponseEntity<>(firstPregnancyResponseDto,HttpStatus.OK);
    }
    public ResponseEntity<HaveKidsResponseDto> HaveKids(String header, HaveKidsRequestDto haveKidsRequestDto) throws Exception {
        Optional<User> user=retrieveUserid(header);
        HaveKidsResponseDto haveKidsResponseDto=new HaveKidsResponseDto();
        if (user.isEmpty()){
            haveKidsResponseDto.setMessage("user not found");
            return new ResponseEntity<>(haveKidsResponseDto, HttpStatus.OK);
        }
        User updateUser=user.get();
        updateUser.setHaveKids(haveKidsRequestDto.isHaveKids());
        updateUser.setLastState(LastState.HAVEKIDS);
        userRepository.save(updateUser);
        haveKidsResponseDto.setMessage("haveKids updated successfully");
        return new ResponseEntity<>(haveKidsResponseDto,HttpStatus.OK);
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

