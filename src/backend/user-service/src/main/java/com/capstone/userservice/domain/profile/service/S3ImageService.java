package com.capstone.userservice.domain.profile.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.capstone.userservice.domain.profile.exception.S3Exception;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.global.exception.Code;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;
    @Value("${cloud.aws.s3.defaultImage}")
    private String defaultImage;

    public String upload(MultipartFile image) {
        //파일 검증
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(Code.NOT_FOUND, "파일을 찾을 수 없습니다.");
        }
        //S3 에 저장된 이미지의 public url 반환
        return this.uploadImage(image);
    }

    private String uploadImage(MultipartFile image) {
        this.validateImageFileExtention(image.getOriginalFilename());
        try {

            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new S3Exception(Code.BAD_REQUEST, "이미지 업로드에 실패 했습니다.");
        }
    }


    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new S3Exception(Code.NOT_FOUND, "이미지 검정에 실패 했습니다.");
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new S3Exception(Code.VALIDATION_ERROR, "지원되는 이미지 형식이 아닙니다.");
        }
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image byte[] 변환

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);

        //S3 요청 할 때 사용할 byteStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            //S3 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            //실제 S3 에 이미지 데이터 put
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new S3Exception(Code.PUT_OBJECT_EXCEPTION, "이미지 업로드에 실패 했습니다.");
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        //DB 에 저장될 이미지 주소
        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public Boolean deleteImageFromS3(String url, User user) {
        String key = getKeyFromImageAddress(url);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
            user.setProfileImage(defaultImage);
            return true;

        } catch (Exception e) {
            throw new S3Exception(Code.IO_EXCEPTION_ON_IMAGE_DELETE, "이미지 삭제에 실패 했습니다.");
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new S3Exception(Code.NOT_FOUND, "존재하지 않는 이미지 입니다.");
        }
    }
}
