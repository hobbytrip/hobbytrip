package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.forum.entity.File;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResponseDto {
    private Long fileId;

    private String fileUrl;

    public static FileResponseDto of(File file){
        return FileResponseDto.builder()
                .fileId(file.getId())
                .fileUrl(file.getFileUrl())
                .build();
    }
}
