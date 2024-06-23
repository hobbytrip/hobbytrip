package capstone.communityservice.global.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public class MultipartUtil {

    private static final String BASE_DIR = "community";

    // 파일 고유 ID 생성
    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    // 확장자만 잘라냄
    public static String getFormat(String contentType) {
        if (StringUtils.hasText(contentType)) {
            return contentType.substring(contentType.lastIndexOf('/') + 1);
        }
        return null;
    }

    // 파일 경로 생성
    public static String createPath(String contentType) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(contentType);
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}

