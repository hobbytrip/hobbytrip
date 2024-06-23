package capstone.communityservice.global.common.service;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import capstone.communityservice.global.util.MultipartUtil;
import com.amazonaws.SdkBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private static final Tika tika = new Tika();

    public String save(MultipartFile multipartFile){
        String contentType = verifiedExtension(multipartFile);

        String fullPath = MultipartUtil.createPath(contentType);

        return amazonS3ResourceStorage.store(fullPath, multipartFile);
    }

    private String verifiedExtension(MultipartFile multipartFile) {
        try {
            String detectedType = tika.detect(multipartFile.getInputStream());

            // 확장자가 jpeg, png인 파일들만 받아서 처리
            if (ObjectUtils.isEmpty(detectedType) || (!detectedType.equals("image/jpeg") && !detectedType.equals("image/png"))) {
                throw new GlobalException(Code.VALIDATION_ERROR, "Use jpeg, png");
            }

            return detectedType;
        } catch (IOException e) {
            throw new GlobalException(Code.IO_ERROR, "File processing error");
        }
    }

    public String delete(String fireUrl){
        awsS3Delete(fireUrl);
        return "이미지 삭제 성공.";
    }

    public String update(MultipartFile multipartFile, String fireUrl){
        awsS3Delete(fireUrl);

        String contentType = verifiedExtension(multipartFile);

        String fullPath = MultipartUtil.createPath(contentType);
        return amazonS3ResourceStorage.store(fullPath, multipartFile);
    }

    private void awsS3Delete(String fileUrl){
        try {
            amazonS3ResourceStorage.delete(fileUrl);
        }
        catch(SdkBaseException e){
            log.error("FileUploadService delete error: {}", e.getMessage());
        }
    }
}
