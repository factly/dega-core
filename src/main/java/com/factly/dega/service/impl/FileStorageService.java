package com.factly.dega.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final String mediaStorageRootDir;

    @Autowired
    public FileStorageService(@Value("${dega.media.upload-dir}") String uploadDir) {
        this.mediaStorageRootDir = uploadDir;
    }

    private Path createDirs(String clientID) {
        String clientDir = (mediaStorageRootDir.endsWith("/")) ?
            mediaStorageRootDir + clientID:
            mediaStorageRootDir + System.getProperty("file.separator")  + clientID;

        Path clientPath = Paths.get(clientDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(clientPath);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        return clientPath;
    }

    public String storeFile(MultipartFile file, Object client) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path clientPath = createDirs((String) client);
            Path targetLocation = clientPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, Object client) {
        try {
            Path clientPath = createDirs((String) client);
            Path filePath = clientPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}
