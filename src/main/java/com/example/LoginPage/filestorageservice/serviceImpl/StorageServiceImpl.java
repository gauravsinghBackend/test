package com.zevo360.filestorageservice.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.zevo360.filestorageservice.dto.PhotoVaultResponseDto;
import com.zevo360.filestorageservice.entity.S3Storage;
import com.zevo360.filestorageservice.entity.User;
import com.zevo360.filestorageservice.exception.FileUploadingFailedException;

import com.zevo360.filestorageservice.repository.S3StorageRepository;
import com.zevo360.filestorageservice.repository.UserRepository;
import com.zevo360.filestorageservice.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    S3StorageRepository s3StorageRepository;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;


    private String uploadFile(MultipartFile file,String category,Long userId){
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
        s3Client.putObject(new PutObjectRequest(bucketName,  userId + "/" + category + "/" + fileName,fileObj));
        List<S3ObjectSummary> result = listObjects(userId + "/" + category + "/" +fileName, bucketName);
        fileObj.delete();
        if( result != null && !result.isEmpty()) {
            S3ObjectSummary s = result.get(0);
            String url = "https://" + bucketName + ".s3.amazonaws.com/" + s.getKey();
            S3Storage s3Storage = new S3Storage();
            s3StorageRepository.save(s3Storage);
            return url;
        }
        return null;
    }


    private List<S3ObjectSummary> listObjects(String location, String bucket){
        ObjectListing objectListing = s3Client.listObjects(bucket,location);
        return objectListing.getObjectSummaries();
    }
    @Override
    public byte[] downloadFile(String fileName){
        S3Object s3Object =  s3Client.getObject(bucketName,fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteFile(String fileName){
        s3Client.deleteObject(bucketName,fileName);
        return fileName + "File successfully removed";
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

    @Override
    public List<S3ObjectSummary> getAllFiles(){

        ObjectListing listObjectsV2Result = s3Client.listObjects(bucketName);
        return listObjectsV2Result.getObjectSummaries().stream().collect(Collectors.toList());
    }

    private List<PhotoVaultResponseDto> getS3Storages(List<S3Storage> s3StorageList){

        Map<LocalDate,List<S3Storage>> map = new HashMap<>();
        for(S3Storage s3Storage : s3StorageList){
            LocalDate date = s3Storage.getUploadDate();
            if(!map.containsKey(date)){
                List<S3Storage> s3StoragesList1 = new ArrayList<>();
                s3StoragesList1.add(s3Storage);
                map.put(date,s3StoragesList1);
            } else {
                List<S3Storage> s3StoragesList2 = map.get(date);
                s3StoragesList2.add(s3Storage);
                map.put(date,s3StoragesList2);
            }
        }

        List<PhotoVaultResponseDto> result = new ArrayList<>();
        Set<Map.Entry<LocalDate,List<S3Storage>>> set = map.entrySet();
        for(Map.Entry<LocalDate,List<S3Storage>> ele : set){
            LocalDate date = ele.getKey();
            List<S3Storage> s3Storages = ele.getValue();
            PhotoVaultResponseDto photoVaultResponseDto = new PhotoVaultResponseDto(date,s3Storages);
            result.add(photoVaultResponseDto);
        }
        return result;

    }

    @Override
    public List<PhotoVaultResponseDto> getS3StorageByCategoryAndUserId(String category,Integer pageNumber,Integer limit,Long userId) {
        if(category.equalsIgnoreCase("all")){
            return getPaginatedPhotoUrls(pageNumber,limit,userId);
        }
        List<S3Storage> s3StorageList = s3StorageRepository.findNRecordsPerGroupPaged1(pageNumber,limit,userId,category);
        return getS3Storages(s3StorageList);
    }



    @Override
    public List<String> uploadFileInS3Bucket(String category, List<MultipartFile> file,Long userId) {
        List<String> uploadedList = new ArrayList<>();
        for(MultipartFile multipartFile : file){
            String currentResult = uploadFile(multipartFile,category,userId);
            uploadedList.add(currentResult);
        }
        return uploadedList;
    }

    @Override
    public List<PhotoVaultResponseDto> getPaginatedPhotoUrls(int page, int limit, Long userId) {

        List<S3Storage> s3StorageList = s3StorageRepository.findNRecordsPerGroupPaged(page,limit,userId);
        return getS3Storages(s3StorageList);
    }



    @Override
    public List<S3Storage> getS3ByUploadeDate(int page, int limit, Long userId, LocalDate uploadDate) {

        Pageable p =  PageRequest.of(page - 1, limit);
        return s3StorageRepository.findS3StorageByUserIdAndDate(userId,uploadDate,p);

    }

}
