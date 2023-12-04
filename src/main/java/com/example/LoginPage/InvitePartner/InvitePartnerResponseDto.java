package com.example.LoginPage.InvitePartner;

import lombok.Data;

@Data
public class InvitePartnerResponseDto {
    private String message;//Success/failed
    private InvitePartnerStatus status;
}
