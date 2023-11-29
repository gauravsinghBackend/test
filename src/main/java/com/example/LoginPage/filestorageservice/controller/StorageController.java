package com.zevo360.filestorageservice.controller;



import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.zevo360.filestorageservice.dto.PhotoVaultResponseDto;
import com.zevo360.filestorageservice.encryption.RetrieveUseId;
import com.zevo360.filestorageservice.entity.S3Storage;
import com.zevo360.filestorageservice.entity.User;
import com.zevo360.filestorageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/v1.0/s3Storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private RetrieveUseId retrieveUseId;

    private static final int PAGE_NUMBER = 1;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam String category, @RequestBody List<MultipartFile> files,@RequestHeader ("Authorization") String header){
        Long userId = null;
        try {
            Optional<User> user = retrieveUseId.retrieveUserid(header);
            userId = user.get().getId();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
        List<String> uploadList = storageService.uploadFileInS3Bucket(category,files,userId);
        return new ResponseEntity<>(uploadList, HttpStatus.OK);
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
        byte[] data =  storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type","application/octet-stream")
                .header("Content-disposition","attachment; fileName=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        return new ResponseEntity<>(storageService.deleteFile(fileName),HttpStatus.OK);
    }

    @GetMapping("/getAllFiles")
    public List<S3ObjectSummary> getAllFiles() {
        return storageService.getAllFiles();
    }

    @GetMapping("/getS3StoragesByCategory")
    public ResponseEntity<List<PhotoVaultResponseDto>> getS3StorageByCategory(@RequestParam String category, @RequestParam Integer limit,@RequestHeader ("Authorization") String header){
        Long userId = null;
        try {
            Optional<User> user = retrieveUseId.retrieveUserid(header);
            userId = user.get().getId();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
        return new ResponseEntity<>(storageService.getS3StorageByCategoryAndUserId(category,PAGE_NUMBER,limit,userId),HttpStatus.OK);
    }

    @GetMapping("/pagination/getS3ByUploadeDate")
    public ResponseEntity<List<S3Storage>> getS3ByUploadeDate(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "2") int limit, @RequestParam LocalDate uploadDate,@RequestHeader ("Authorization") String header){
        Long userId = null;
        try {
            Optional<User> user = retrieveUseId.retrieveUserid(header);
            userId = user.get().getId();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
        List<S3Storage> response = storageService.getS3ByUploadeDate(pageNumber,limit,userId,uploadDate);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
