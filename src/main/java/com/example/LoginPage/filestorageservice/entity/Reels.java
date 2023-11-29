package com.example.LoginPage.filestorageservice.entity;

import com.example.LoginPage.filestorageservice.controller.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="reels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull(message = "name of the reel cannot be null")
    private String name;
//    @NotNull(message = "Please enter reel url")
    @Column(name="reel_url")
    private String url;
//    @NotNull(message = "Please enter the tag.")
    private String tag;

//    @NotNull(message = "Please enter status")

    @Enumerated(EnumType.STRING)
    private Status status;

//    @NotNull(message = "Caption cannot be null")
    private String caption;
//    @NotNull(message="Please enter the age for which this reel is applicable")
    @Column(name="age_category")
    private Integer ageCategory;
    @Column(name="bucket_name")
    private String bucketName;
    @Column(name="category")
    private String category;
    @Column(name="user_id")
    private Long userId;
    @Column(name="upload_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate uploadDate;

    public Reels(String name,String url,String tag,Status status,String caption,Integer ageCategory,String bucketName,String category,Long userId,LocalDate uploadDate){
        this.name = name;
        this.url = url;
        this.tag = tag;
        this.status = status;
        this.caption = caption;
        this.ageCategory = ageCategory;
        this.bucketName = bucketName;
        this.category = category;
        this.userId = userId;
        this.uploadDate = uploadDate;
    }
}
