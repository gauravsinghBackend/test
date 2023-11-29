package com.example.LoginPage.filestorageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name = "s3_storage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class S3Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="bucket_name")
    private String bucketName;
    @Column(name="url")
    private String url;
    @Column(name="category")
    private String category;
    @Column(name="user_id")
    private Long userId;
    @Column(name="upload_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate uploadDate;

    public S3Storage(String bucketName, String url, String category,Long userId,LocalDate uploadDate,String thumbNailUrl){
        this.bucketName = bucketName;
        this.url = url;
        this.category = category;
        this.uploadDate = uploadDate;
        this.userId = userId;
    }
}
