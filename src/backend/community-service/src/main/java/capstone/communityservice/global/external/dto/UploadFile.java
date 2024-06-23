package capstone.communityservice.global.external.dto;

import lombok.Getter;

@Getter
public class UploadFile {

    private String fileUrl;
    private String storeFileName;
    private String originalFilename;

    public UploadFile(String storeFileName, String originalFilename) {
        this.storeFileName = storeFileName;
        this.originalFilename = originalFilename;
    }
}
