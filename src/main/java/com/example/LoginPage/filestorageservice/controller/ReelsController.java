package com.example.LoginPage.filestorageservice.controller;

import com.example.LoginPage.Models.User;
import com.example.LoginPage.filestorageservice.encryption.RetrieveUseId;
import com.example.LoginPage.filestorageservice.entity.Reels;
import com.example.LoginPage.filestorageservice.service.ReelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class ReelsController {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private RetrieveUseId retrieveUseId;

    @PostMapping("/upload")
    public ResponseEntity<List<Reels>> uploadFile(@RequestBody String category, @RequestBody List<MultipartFile> files, @RequestHeader("Authorization") String header,
                                                  @RequestBody String name, @RequestBody String tag, @RequestBody Status status, @RequestBody String caption, @RequestBody Integer ageCategory){
        Long userId = null;
        try {
            Optional<User> user = retrieveUseId.retrieveUserid(header);
            userId = user.get().getId();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
        List<Reels> uploadList = reelsService.uploadFileInS3Bucket(category,files,userId,name,tag,status,caption,ageCategory);
        return new ResponseEntity<>(uploadList, HttpStatus.OK);
    }
}
