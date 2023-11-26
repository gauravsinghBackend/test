package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class KidAddition {
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
            KidResponseDto kidResponseDto=new KidResponseDto();
            //Validate if Header is null
            if (header==null) {
                kidResponseDto.setMeassage("Header Missing");
                return new ResponseEntity<>(kidResponseDto,HttpStatus.OK);
            }
            String token = header.substring(7);
            TokenData tokenData = tokenManager.decryptToken(token);
            if (tokenData==null)
            {
                kidResponseDto.setMeassage("Incorrect Header");
                return new ResponseEntity<>(kidResponseDto,HttpStatus.OK);
            }
            Optional<User> user = userRepository.findById(tokenData.getUserId());
            //validate if user is null
            if (user.get().getId()==null) {
                kidResponseDto.setMeassage("User is Invalid or Null");
                return new ResponseEntity<>(kidResponseDto,HttpStatus.OK);
            }
            Kid kid=kidService.addKid(user.get(), kidRequestDto.getName(), kidRequestDto.getDob(), kidRequestDto.getProfilePicture(),kidRequestDto.getGender());
            kidResponseDto.setId(kid.getId());
            kidResponseDto.setMeassage("kid saved successfully");
            return new ResponseEntity<>(kidResponseDto, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception();
        }

    }

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
