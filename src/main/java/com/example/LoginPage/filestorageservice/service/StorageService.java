package com.zevo360.filestorageservice.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.zevo360.filestorageservice.dto.PhotoVaultResponseDto;
import com.zevo360.filestorageservice.entity.S3Storage;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StorageService {


    List<PhotoVaultResponseDto> getS3StorageByCategoryAndUserId(String category, Integer pageNumber, Integer numberOfRecords, Long userId);

    List<S3ObjectSummary> getAllFiles();



    String deleteFile(String fileName);

    byte[] downloadFile(String fileName);

    List<String> uploadFileInS3Bucket(String category, List<MultipartFile> file,Long userId);

    List<PhotoVaultResponseDto> getPaginatedPhotoUrls(int page, int limit, Long userId);


    List<S3Storage> getS3ByUploadeDate(int page, int limit, Long userId, LocalDate localDate);
}
