package com.zevo360.filestorageservice.dto;

import com.zevo360.filestorageservice.controller.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
