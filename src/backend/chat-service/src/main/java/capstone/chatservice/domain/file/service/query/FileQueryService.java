package capstone.chatservice.domain.file.service.query;

import capstone.chatservice.domain.file.dto.response.DownloadFileResponse;
import capstone.chatservice.infra.S3.FileDownload;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
@RequiredArgsConstructor
public class FileQueryService {

    private final FileDownload fileDownload;

    public DownloadFileResponse downloadAttachFile(String storeFileName, String originalFileName)
            throws MalformedURLException {

        fileDownload.validateFileExistsAtUrl(storeFileName);

        UrlResource resource = new UrlResource(fileDownload.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return new DownloadFileResponse(resource, contentDisposition);
    }
}
