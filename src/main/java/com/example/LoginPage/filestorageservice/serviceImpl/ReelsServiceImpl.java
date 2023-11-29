package com.zevo360.filestorageservice.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.zevo360.filestorageservice.controller.Status;
import com.zevo360.filestorageservice.dto.ReelsDto;
import com.zevo360.filestorageservice.entity.Reels;
import com.zevo360.filestorageservice.entity.User;
import com.zevo360.filestorageservice.exception.FileUploadingFailedException;
import com.zevo360.filestorageservice.repository.ReelsRepository;
import com.zevo360.filestorageservice.repository.UserRepository;
import com.zevo360.filestorageservice.service.ReelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReelsServiceImpl implements ReelsService {

    @Autowired
    private ReelsRepository reelsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    private Reels uploadFile(MultipartFile file,String category, Long userId,String name, String tag, Status status, String caption, Integer ageCategory) {
        User user = userRepository.getUsersById(userId);
        if(user == null){
            throw new RuntimeException("Invalid user id");
        }
        File fileObj = convertMultiPartFile(file);
        /*
        currentTimeMillis() is used to append the time stamp in file name so that
        one can maintain the uniqueness of file
         */
        String fileName =  System.currentTimeMillis()+"_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, "admin/" +   userId + "/" + category + "/" + fileName,fileObj));
        List<S3ObjectSummary> result = listObjects("admin/" + userId + "/" + category + "/" +fileName, bucketName);
        fileObj.delete();
        if( result != null && !result.isEmpty()) {
            S3ObjectSummary s = result.get(0);
            String url = "https://" + bucketName + ".s3.amazonaws.com/" + s.getKey();
            Reels reels = new Reels(name,url,tag,status,caption,ageCategory,s.getBucketName(),category,userId,LocalDate.now());

            Reels r = reelsRepository.save(reels);
            return r;
        }
        return null;
    }

    @Override
    public List<Reels> uploadFileInS3Bucket(String category, List<MultipartFile> file, Long userId, String name, String tag, Status status, String caption, Integer ageCategory) {
        List<Reels> uploadedList = new ArrayList<>();
        for(MultipartFile multipartFile : file){
            Reels currentResult = uploadFile(multipartFile,category,userId,name,tag,status,caption,ageCategory);
            uploadedList.add(currentResult);
        }
        return uploadedList;
    }

    private List<S3ObjectSummary> listObjects(String location, String bucket){
        ObjectListing objectListing = s3Client.listObjects(bucket,location);
        return objectListing.getObjectSummaries();
    }


    private File convertMultiPartFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        } catch(IOException e){
            throw new FileUploadingFailedException("Failed to upload the file..Please try again");
        }
        return convertedFile;
    }
}
