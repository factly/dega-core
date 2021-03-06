package com.factly.dega.web.rest;

import com.factly.dega.service.MediaService;
import com.factly.dega.service.impl.FileStorageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/dega-content")
public class DegaContentResource {

    private final Logger log = LoggerFactory.getLogger(DegaContentResource.class);


    private final MediaService mediaService;
    private final String mediaStorageRootDir;

    public DegaContentResource(MediaService mediaService, @Value("${dega.media.upload-dir}") String mediaStorageRootDir) {
        this.mediaService = mediaService;
        this.mediaStorageRootDir = mediaStorageRootDir;
    }

    @GetMapping("/{clientId}/{year}/{month}/{fileName:.+}")
    public ResponseEntity<Resource> downloadMedia(@PathVariable String clientId,
                                                  @PathVariable String year,
                                                  @PathVariable String month,
                                                  @PathVariable String fileName,
                                                  HttpServletRequest request) {
        // TODO validation 
        // Load file as Resource
        int yr = Integer.parseInt(year);
        int mon = Integer.parseInt(month);
        Resource resource = new FileStorageServiceImpl(mediaStorageRootDir).loadFileAsResource(fileName, clientId, yr, mon);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
