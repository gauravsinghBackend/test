package com.example.LoginPage.OnBoarding;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.KidAddition.KidResponseDto;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@CrossOrigin
@RestController
public class OnBoardingController {
    @Autowired
    private OnBoardingService onBoardingService;

    @PostMapping("/pregnantorchild")
    public ResponseEntity<PregnantChildResponseDto> PregnantOrChild(@RequestHeader("Authorization") String header, @RequestBody PregnantChildRequestDto pregnantChildRequestDto) throws Exception {
        try {
            return onBoardingService.PregnantorChild(header, pregnantChildRequestDto);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException();
        }

    }
}
