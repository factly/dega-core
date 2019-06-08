package com.factly.dega.service.impl;

import com.factly.dega.service.StorageService;
import com.factly.dega.web.rest.util.CommonUtil;
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
import java.util.HashMap;
import java.util.Map;


public class FileStorageServiceImpl implements StorageService {

    private final String mediaStorageRootDir;

    public FileStorageServiceImpl(String mediaStorageRootDir) {
        this.mediaStorageRootDir = mediaStorageRootDir;
    }

    @Override
    public String storeFile(MultipartFile file, String client, int year, int month) {
        // Normalize file name
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        // remove all chars except a-z, 0-9
        final String cleanFileName = CommonUtil.removeSpecialCharsFromString(name);

        try {
            // Check if the file's name contains invalid characters
            if(cleanFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + cleanFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path clientPath = createDirs(client, year, month);

            Map<String, Path> fileMap = new HashMap<>();
            Files.list(clientPath).forEach(p -> {
                String fName = p.getFileName().toString();
                fileMap.put(getFileNameWithoutExtension(fName), p);
            });
            String ext = getFileNameExtension(cleanFileName);
            String uniqueFileName = getFileName(fileMap, getFileNameWithoutExtension(cleanFileName));
            String fileName = uniqueFileName + ext;

            Path targetLocation = clientPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + cleanFileName + ". Please try again!", ex);
        }
    }

    private String getFileNameWithoutExtension(String fName) {
        int idx = fName.lastIndexOf(".");
        if (idx != -1) {
            return fName.substring(0, idx);
        }
        return fName;
    }

    private String getFileNameExtension(String fName) {
        int idx = fName.lastIndexOf(".");
        if (idx != -1) {
            return fName.substring(idx);
        }
        return "";
    }

    private String getFileName(Map<String, Path> fileMap, String fileName) {
        if (fileMap.get(fileName) != null) {
            return getFileName(fileMap, fileName + "-1");
        }
        return fileName;
    }

    public Resource loadFileAsResource(String fileName, Object client, int year, int day) {
        try {
            Path clientPath = createDirs((String) client, year, day);
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

    private Path createDirs(String clientID, int year, int month) {
        String fileSep = System.getProperty("file.separator");

        String rootDir = "." + fileSep + mediaStorageRootDir;
        String clientDir = (rootDir.endsWith(fileSep)) ?
            rootDir + clientID + fileSep + year + fileSep + month :
            rootDir + fileSep  + clientID + fileSep + year + fileSep + month + fileSep;

        Path clientPath = Paths.get(clientDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(clientPath);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        return clientPath;
    }
}
