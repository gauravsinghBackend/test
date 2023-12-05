package com.example.LoginPage.KidAddition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KidResponseDto {
    private Long id;
    private String message;

    public KidResponseDto(String message) {
        this.message=message;
    }
}
