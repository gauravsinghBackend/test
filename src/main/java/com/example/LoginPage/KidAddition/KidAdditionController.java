package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.InvitePartner.InvitePartnerResponseDto;
import com.example.LoginPage.InvitePartner.InvitePartnerStatus;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.LoginSignUp.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class KidAdditionController {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KidService kidService;
    @Autowired
    private KidRepository kidRepository;
    @PostMapping("/addkids")
    public ResponseEntity<KidResponseDto>addKid(@RequestHeader("Authorization") String header, @RequestBody KidRequestDto kidRequestDto) throws Exception {
        try {
            KidResponseDto kidResponseDto=kidService.addKid(header,kidRequestDto);
            return new ResponseEntity<>(kidResponseDto,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new KidResponseDto(StringAll.kidAddFailed));
        }

    }
    //Will add this method later if delete icon is there
    public void deleteKid(Long kidId) {
        Optional<Kid> optionalKid = kidRepository.findById(kidId);
        if (optionalKid.isPresent()) {
            Kid kid = optionalKid.get();
            kid.setDeleted(true);
            kidRepository.save(kid);
        } else {
            // the case where the Kid with the given ID is not found
        }
    }
}

