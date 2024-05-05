package capstone.chatservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

    private String fileUrl;
    private String storeFileName;
    private String originalFilename;

    public UploadFile(String storeFileName, String originalFilename) {
        this.storeFileName = storeFileName;
        this.originalFilename = originalFilename;
    }
}
