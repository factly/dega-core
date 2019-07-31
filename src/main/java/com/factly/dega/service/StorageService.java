package com.factly.dega.service;

import com.factly.dega.domain.MediaUrls;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService  {
    MediaUrls storeFile(MultipartFile file, String client, int year, int month) throws IOException;
}
