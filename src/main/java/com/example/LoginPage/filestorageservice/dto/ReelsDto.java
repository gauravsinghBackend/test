package com.example.LoginPage.filestorageservice.dto;

import com.example.LoginPage.filestorageservice.controller.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReelsDto {
    private String name;
    private String tag;
    private Status status;
    private String caption;
    private Integer ageCategory;
    private String category;

}
