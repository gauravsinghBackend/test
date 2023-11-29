package com.zevo360.filestorageservice.service;

import com.zevo360.filestorageservice.controller.Status;
import com.zevo360.filestorageservice.dto.ReelsDto;
import com.zevo360.filestorageservice.entity.Reels;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReelsService {

    List<Reels> uploadFileInS3Bucket(String category, List<MultipartFile> files, Long userId, String name, String tag, Status status,String caption, Integer ageCategory);
}
