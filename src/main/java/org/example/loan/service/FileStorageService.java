package org.example.loan.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    void save(MultipartFile file) throws IOException;

}
