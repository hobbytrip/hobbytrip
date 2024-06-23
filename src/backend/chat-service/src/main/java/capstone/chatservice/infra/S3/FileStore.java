package capstone.chatservice.infra.S3;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.file.exception.FileException;
import capstone.chatservice.global.exception.Code;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileStore {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private static final int FILE_COUNT = 10;
    private static final String BASE_DIR = "chat/";

    private final AmazonS3Client amazonS3Client;

    private UploadFile convertFile(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        return new UploadFile(storeFileName, originalFilename);
    }

    // 파일을 S3에 저장
    private String storeFile(MultipartFile multipartFile, String storeFileName) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(bucketName, storeFileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new FileException(Code.FILE_UPLOAD_FAILED);
        }

        return amazonS3Client.getUrl(bucketName, storeFileName).toString();
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {

        validateFileUploadCount(multipartFiles);

        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            UploadFile uploadFile = convertFile(multipartFile);
            String storeFileName = uploadFile.getStoreFileName();
            String fileUrl = storeFile(multipartFile, storeFileName);
            uploadFile.setFileUrl(fileUrl);
            uploadFiles.add(uploadFile);
        }

        return uploadFiles;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return BASE_DIR + uuid + "." + ext;
    }

    // 확장자 추출 메소드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private void validateFileUploadCount(List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > FILE_COUNT) {
            throw new FileException(Code.FILE_COUNT_EXCEEDED);
        }
    }
}
