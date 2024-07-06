package capstone.communityservice.domain.forum.dto.response;

import capstone.communityservice.domain.forum.entity.File;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResponse {
    private Long fileId;

    private String fileUrl;

    public static FileResponse of(File file){
        return FileResponse.builder()
                .fileId(file.getId())
                .fileUrl(file.getFileUrl())
                .build();
    }
}
