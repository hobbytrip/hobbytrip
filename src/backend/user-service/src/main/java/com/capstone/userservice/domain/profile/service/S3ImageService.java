package com.capstone.userservice.domain.profile.service;


import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

//    public String upload(MultipartFile image) {
//        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
//            throw new
//        }
//    }
}
