package com.example.LoginPage.filestorageservice.service;

import com.example.LoginPage.filestorageservice.controller.Status;
import com.example.LoginPage.filestorageservice.entity.Reels;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReelsService {

    List<Reels> uploadFileInS3Bucket(String category, List<MultipartFile> files, Long userId, String name, String tag, Status status, String caption, Integer ageCategory);
}
