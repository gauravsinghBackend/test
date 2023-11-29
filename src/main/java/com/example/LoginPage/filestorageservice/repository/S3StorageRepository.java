package com.zevo360.filestorageservice.repository;

import com.zevo360.filestorageservice.entity.S3Storage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface S3StorageRepository extends JpaRepository<S3Storage,Long> {

@Query(value = "WITH numbered AS (\n" +
        "  SELECT\n" +
        "    s.*,\n" +
        "    ROW_NUMBER() OVER (PARTITION BY s.upload_date ORDER BY s.upload_date DESC, s.id) as row_num,\n" +
        "    MAX(s.upload_date) OVER (PARTITION BY s.user_id) as max_upload_date\n" +
        "  FROM s3_storage s\n" +
        "  WHERE s.user_id = :userId\n" +
        ")\n" +
        "SELECT *\n" +
        "FROM numbered\n" +
        "WHERE\n" +
        "  row_num > :pageSize * (:pageNumber - 1)\n" +
        "  AND row_num <= :pageSize * :pageNumber\n" +
        "ORDER BY max_upload_date DESC, id DESC;\n", nativeQuery = true)
List<S3Storage> findNRecordsPerGroupPaged(
        @Param("pageNumber") int pageNumber,
        @Param("pageSize") int pageSize,
        @Param("userId") Long userId);



    @Query(value = "WITH numbered AS (\n" +
            "  SELECT\n" +
            "    s.*,\n" +
            "    ROW_NUMBER() OVER (PARTITION BY s.user_id, s.category, s.upload_date ORDER BY s.upload_date DESC, s.id) as row_num,\n" +
            "    MAX(s.upload_date) OVER (PARTITION BY s.user_id, s.category) as max_upload_date\n" +
            "  FROM s3_storage s\n" +
            "  WHERE s.user_id = :userId AND s.category = :category\n" +
            ")\n" +
            "SELECT *\n" +
            "FROM numbered\n" +
            "WHERE\n" +
            "  row_num > :pageSize * (:pageNumber - 1)\n" +
            "  AND row_num <= :pageSize * :pageNumber\n" +
            "ORDER BY max_upload_date DESC, id DESC;", nativeQuery = true)
    List<S3Storage> findNRecordsPerGroupPaged1(
            @Param("pageNumber") int pageNumber,
            @Param("pageSize") int pageSize,
            @Param("userId") Long userId,
            @Param("category") String category);


    @Query(value = "SELECT s FROM S3Storage s WHERE s.uploadDate =?2 AND s.userId =?1")
    List<S3Storage> findS3StorageByUserIdAndDate(
             Long userId,
             LocalDate localDate,
             Pageable p
        );
}
