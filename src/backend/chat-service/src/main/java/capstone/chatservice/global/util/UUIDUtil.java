package capstone.chatservice.global.util;

import java.util.UUID;

public class UUIDUtil {

    // Private 생성자로 인스턴스 생성 방지
    private UUIDUtil() {
        throw new RuntimeException("This is a utility class and cannot be instantiated");
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
