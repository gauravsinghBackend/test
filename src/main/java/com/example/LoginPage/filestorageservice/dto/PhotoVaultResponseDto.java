package com.zevo360.filestorageservice.dto;

import com.zevo360.filestorageservice.entity.S3Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoVaultResponseDto {
    private LocalDate date;
    private List<S3Storage> photos;

}
