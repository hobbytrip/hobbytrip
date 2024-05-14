package capstone.chatservice.infra.S3;

import capstone.chatservice.domain.model.UploadFile;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    @Value("${file.dir}")
    private String fileDir;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;

    private UploadFile convertFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        return new UploadFile(storeFileName, originalFilename);
    }

    private List<UploadFile> convertFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(convertFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // 파일을 S3에 저장
    private String storeFile(MultipartFile multipartFile, String storeFileName) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, storeFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 오류");
        }

        return amazonS3Client.getUrl(bucketName, storeFileName).toString();
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        // 파일 업로드 개수 검증(10개 이하로 정의)
        if (multipartFiles.size() > 10) {
            throw new RuntimeException("파일 업로드 개수 오류");
        }

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
        return uuid + "." + ext;
    }

    // 확장자 추출 메소드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }
}
