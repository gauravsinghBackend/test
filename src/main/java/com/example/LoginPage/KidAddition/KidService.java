package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Models.User;

public interface KidService {
    KidResponseDto addKid(String header, KidRequestDto kidRequestDto) throws Exception;
}
