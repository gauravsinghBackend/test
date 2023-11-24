package com.example.LoginPage.InvitePartner;

import com.example.LoginPage.DTO.InvitePartnerRequestDto;
//import com.example.LoginPage.Repository.TokenRepository;
import com.example.LoginPage.Repository.TokenRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//
@Controller
public class PartnerController {
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/invite")
    public ResponseEntity<String> invitePartner(@RequestHeader("Authorization") String header, @RequestBody InvitePartnerRequestDto invitePartnerRequestDto) {
        try {
            String result = partnerService.InvitePartner(header, invitePartnerRequestDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inviting partner: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
