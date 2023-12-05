package com.example.LoginPage.InvitePartner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitePartnerResponseDto {
    private String message;
    private InvitePartnerStatus status;//Success/failed
}
