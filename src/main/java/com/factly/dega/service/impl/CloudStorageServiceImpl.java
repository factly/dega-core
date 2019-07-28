package com.factly.dega.service.impl;

import com.factly.dega.service.StorageService;
import com.factly.dega.utils.FileNameUtils;
import com.factly.dega.web.rest.MediaResource;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class CloudStorageServiceImpl implements StorageService {

    private static Storage storage = null;
    private final Logger log = LoggerFactory.getLogger(CloudStorageServiceImpl.class);

    static {
        try {
            storage = StorageOptions.getDefaultInstance().getService();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String bucketName;
    private FileNameUtils fileNameUtils;

    public CloudStorageServiceImpl(String bucketName, FileNameUtils fileNameUtils) {
        this.bucketName = bucketName;
        this.fileNameUtils = fileNameUtils;
    }

    @Override
    public String storeFile(MultipartFile file, String client, int year, int month) throws IOException {
        String fileName = fileNameUtils.getFileNameString(file, client, year, month);

        // the inputstream is closed by default, so we don't need to close it here
        BlobInfo blobInfo =
            storage.create(
                BlobInfo
                    .newBuilder(bucketName, fileName)
                    // Modify access list to allow all users with link to read file
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                    .build(),
                file.getBytes());

        // return the public download link
        return blobInfo.getMediaLink();
    }

}
