package com.example.LoginPage.InvitePartner;

//import com.example.LoginPage.LoginSignUp.TokenRepository;
import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.LoginSignUp.TokenRepository;
import com.example.LoginPage.LoginSignUp.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

//
@RestController
public class PartnerController {
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/invite")
    public ResponseEntity<InvitePartnerResponseDto> invitePartner(@RequestHeader("Authorization") String header, @RequestBody InvitePartnerRequestDto invitePartnerRequestDto) {
        InvitePartnerResponseDto invitePartnerResponseDto=new InvitePartnerResponseDto();
        try {
            invitePartnerResponseDto= partnerService.invitePartner(header, invitePartnerRequestDto);
            return new ResponseEntity<>(invitePartnerResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InvitePartnerResponseDto(StringAll.invitedFailed,InvitePartnerStatus.FAILED));
        }
    }
}
