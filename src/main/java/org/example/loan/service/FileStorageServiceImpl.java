package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(
                    file.getInputStream(),
                    Paths.get(uploadPath).resolve(Objects.requireNonNull(file.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = Paths.get(uploadPath).resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BaseException(ResultType.FILE_NOT_EXIST);
            }

        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(uploadPath), 1).filter(path -> !path.equals(Paths.get(uploadPath)));
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }

}
