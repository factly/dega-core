package com.factly.dega.utils;

import com.factly.dega.web.rest.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileNameUtils {

    private final String mediaStorageRootDir;

    public FileNameUtils(@Value("${dega.media.upload-dir}") String mediaStorageRootDir) {
        this.mediaStorageRootDir = mediaStorageRootDir;
    }

    public String getFileNameString(MultipartFile file, String clientID, int year, int month) {
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        // remove all chars except a-z, 0-9
        final String cleanFileName = CommonUtil.removeSpecialCharsFromString(name);


        // Check if the file's name contains invalid characters
        if(cleanFileName.contains("..")) {
            throw new RuntimeException("Sorry! Filename contains invalid path sequence " + cleanFileName);
        }

        // Copy file to the target location (Replacing existing file with the same name)
        final String FILE_SEPARATOR = System.getProperty("file.separator");
        String path = mediaStorageRootDir + FILE_SEPARATOR  + clientID
            + FILE_SEPARATOR + year + FILE_SEPARATOR + month + FILE_SEPARATOR + System.currentTimeMillis() + "-" + cleanFileName;
        return path;
    }

}
