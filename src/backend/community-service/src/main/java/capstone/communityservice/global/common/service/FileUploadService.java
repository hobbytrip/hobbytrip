package capstone.communityservice.global.common.service;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import capstone.communityservice.global.util.MultipartUtil;
import com.amazonaws.SdkBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public String save(MultipartFile multipartFile){
        verifiedExtension(multipartFile);

        String fullPath = MultipartUtil.createPath(multipartFile);

        return amazonS3ResourceStorage.store(fullPath, multipartFile);
    }

    private void verifiedExtension(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        // 확장자가 jpeg, png인 파일들만 받아서 처리
        if(ObjectUtils.isEmpty(contentType) | (!contentType.contains("image/jpeg") & !contentType.contains("image/png")))
            throw new GlobalException(Code.VALIDATION_ERROR, "Use jpeg, png");
    }

    public String delete(String fireUrl){
        awsS3Delete(fireUrl);
        return "이미지 삭제 성공.";
    }

    public String update(MultipartFile multipartFile, String fireUrl){
        awsS3Delete(fireUrl);

        verifiedExtension(multipartFile);

        String fullPath = MultipartUtil.createPath(multipartFile);
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
