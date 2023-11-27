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
@RestController("/onboarding")
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
    @PostMapping("/pregnanttrue")
    public ResponseEntity<PregnantTrueResponseDto> PregnantTrue(@RequestHeader("Authorization") String header, @RequestBody PregnantTrueRequestDto pregnantTrueRequestDto) throws Exception {
        try{
            return onBoardingService.PregnantTrue(header,pregnantTrueRequestDto);
        }
        catch(Exception e)
        {
            throw new Exception();
        }
    }
    @PostMapping("/isfirstpregnancy")
    public ResponseEntity<FirstPregnancyResponseDto> FirstPregnancy(@RequestHeader("Authorization") String header, @RequestBody FirstPregnancyRequestDto firstPregnancyRequestDto) throws Exception {
        try{
            return onBoardingService.FirstPregnancy(header,firstPregnancyRequestDto);
        }
        catch(Exception e)
        {
            throw new Exception();
        }
    }
    @PostMapping("/havekids")
    public ResponseEntity<HaveKidsResponseDto> HaveKids(@RequestHeader("Authorization") String header, @RequestBody HaveKidsRequestDto haveKidsRequestDto) throws Exception {
        try{
            return onBoardingService.HaveKids(header,haveKidsRequestDto);
        }
        catch(Exception e)
        {
            throw new Exception();
        }
    }
}
