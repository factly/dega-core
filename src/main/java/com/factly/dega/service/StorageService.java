package com.factly.dega.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService  {
    String storeFile(MultipartFile file, String client, int year, int month) throws IOException;
}
