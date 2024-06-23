package capstone.communityservice.global.common.service;

import capstone.communityservice.global.exception.GlobalException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String store(String fullPath, MultipartFile multipartFile) {
        try {
            // MultipartFile의 InputStream을 가져옴
            InputStream inputStream = multipartFile.getInputStream();

            // 파일 메타데이터 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            // S3에 파일 업로드
            amazonS3Client.putObject(bucket, fullPath, inputStream, metadata);

            log.info("AmazonS3Resource Storage File uploaded: {}", fullPath);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException("File Store Error");
        }

        // S3에 업로드된 이미지 URL 반환
        return amazonS3Client.getUrl(bucket, fullPath).toString();
    }

    public void delete(String fileUrl) {
        try {
            // 파일 URL에서 key 추출
            String key = fileUrl.substring(fileUrl.indexOf(bucket) + bucket.length() + 1);
            try {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
            } catch (AmazonServiceException e) {
                log.error(e.getErrorMessage());
                throw new GlobalException(e.getErrorMessage());
            }
            log.info(String.format("AmazonS3ResourceStorage File delete: {} deletion complete", key));
        } catch (Exception e) {
            log.error("AmazonS3ResourceStorage Key extract Error: {}", e.getMessage());
            throw new GlobalException("FAILED_TO_DELETE_FILE");
        }
    }
}
