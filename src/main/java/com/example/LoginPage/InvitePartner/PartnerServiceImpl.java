package com.example.LoginPage.InvitePartner;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.LoginPage.Config.InvitePartnerException;
import com.example.LoginPage.Config.StringAll;
import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.LoginSignUp.TokenRepository;
import com.example.LoginPage.LoginSignUp.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService{
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final InvitePartnerRepositories partnerRepository;

    private TokenManager tokenManager;
    @Autowired
    public PartnerServiceImpl(TokenRepository tokenRepository, UserRepository userRepository,
                              InvitePartnerRepositories partnerRepository, TokenManager tokenManager) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.tokenManager=tokenManager;
    }

    public InvitePartnerResponseDto invitePartner(String header, InvitePartnerRequestDto invitePartnerRequestDto) {
        try {
            // Extract user from token
            String validationHeader = extractHeader(header);
            String[] parts = validationHeader.split("\\.");
            TokenData userId = tokenManager.decryptToken(parts[0]);
            Optional<User> userOptional = userRepository.findById(userId.getUserId());

            if (userOptional.isPresent()) {
                User fetchedUser = userOptional.get();
                InvitePartnerResponseDto invitePartnerResponseDto = new InvitePartnerResponseDto();

                // Check if the partner already exists
                Partner existingPartner = partnerRepository.findByPartnerPhone(invitePartnerRequestDto.getPhone());

                if (existingPartner != null) {
                    invitePartnerResponseDto.setStatus(InvitePartnerStatus.FAILED);
                    invitePartnerResponseDto.setMessage(StringAll.invitedFailed);
                    return invitePartnerResponseDto;
                }

                // Save the partner
                Partner newPartner = new Partner();
                newPartner.setUser(fetchedUser);
                newPartner.setName(invitePartnerRequestDto.getName());
                newPartner.setPartnerPhone(invitePartnerRequestDto.getPhone());
                partnerRepository.save(newPartner);

                invitePartnerResponseDto.setStatus(InvitePartnerStatus.SUCCESS);
                invitePartnerResponseDto.setMessage(StringAll.invitedSuccess);
                return invitePartnerResponseDto;
            } else {
                // User not found
                throw new NotFoundException("User not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace(); // Log the exception or use a logging framework
            throw new InvitePartnerException("Error inviting partner");
        }
    }
    public String extractHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing authentication header");
        }
        return header.substring(7);
    }
}