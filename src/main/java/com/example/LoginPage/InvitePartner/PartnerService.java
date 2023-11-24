package com.example.LoginPage.InvitePartner;

import com.example.LoginPage.DTO.InvitePartnerRequestDto;
import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.Partner;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.Models.UserAuthentificationToken;
import com.example.LoginPage.Repository.PartnerRepository;
import com.example.LoginPage.Repository.TokenRepository;
import com.example.LoginPage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    private TokenManager tokenManager;
    @Autowired
    public PartnerService(TokenRepository tokenRepository, UserRepository userRepository,
                          PartnerRepository partnerRepository,TokenManager tokenManager) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.tokenManager=tokenManager;
    }

    public String InvitePartner(String header, InvitePartnerRequestDto invitePartnerRequestDto) throws Exception {
        // Extract user from token
        String validationHeader = extractHeader(header);
        String[] parts = validationHeader.split("\\.");
        TokenData userId=tokenManager.decryptToken(parts[0]);
        Optional <User> user=userRepository.findById(userId.getUserId());
        User fetchedUser=user.get();
        // Check if the partner already exists
        Partner existingPartner = partnerRepository.findByPartnerPhone(invitePartnerRequestDto.getPhone());
        if (existingPartner != null) {
            return "Partner with the provided phone number already exists";
        }

        // Save the partner
        Partner newPartner = new Partner();
        newPartner.setUser(fetchedUser);
        newPartner.setName(invitePartnerRequestDto.getName());
        newPartner.setPartnerPhone(invitePartnerRequestDto.getPhone());
        partnerRepository.save(newPartner);

        return "Partner invited successfully";
    }
    public String extractHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }
}