package com.factly.dega.service.impl;

import com.factly.dega.service.StorageService;
import com.factly.dega.utils.FileNameUtils;
import org.springframework.web.multipart.MultipartFile;


import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class CloudStorageServiceImpl implements StorageService {

    private String bucketName;
    private FileNameUtils fileNameUtils;
    private String storageURL;

    private static Storage storage = null;
    static {
        try {
            storage = StorageOptions.getDefaultInstance().getService();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public CloudStorageServiceImpl(String bucketName, FileNameUtils fileNameUtils, String storageURL) {
        this.bucketName = bucketName;
        this.fileNameUtils = fileNameUtils;
        this.storageURL = storageURL;
    }

    @Override
    public String storeFile(MultipartFile file, String client, int year, int month) throws IOException {
        String fileName = fileNameUtils.getFileNameString(file, client, year, month);
        storage.create(
            BlobInfo
                .newBuilder(bucketName, fileName)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(),
            file.getBytes());
        return storageURL + fileName;
    }

}
