package com.example.LoginPage.filestorageservice.dto;

import com.example.LoginPage.filestorageservice.entity.S3Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoVaultResponseDto {
    private LocalDate date;
    private List<S3Storage> photos;

}
