package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.FileDTO;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.ApplicationService;
import org.example.loan.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static org.example.loan.dto.ApplicationDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;
    private final FileStorageService fileStorageService;


    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("/{applicationId}")
    public ResponseDTO<Response> update(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseDTO<Void> delete(@PathVariable Long applicationId) {
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("/{applicationId}/terms")
    public ResponseDTO<Boolean> acceptTerms(@PathVariable Long applicationId, @RequestBody AcceptTerms request) {
        return ok(applicationService.acceptTerms(applicationId, request));
    }

    @PostMapping("/files")
    public ResponseDTO<Void> upload(MultipartFile file) throws IllegalStateException, IOException {
        fileStorageService.save(file);
        return ok();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam(value = "fileName") String fileName) throws IllegalStateException, IOException {
        Resource file = fileStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }

    @GetMapping("/files/info")
    public ResponseDTO<List<FileDTO>> getFileInfos() {
        List<FileDTO> fileInfos = fileStorageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            return FileDTO.builder().name(fileName)
                    .url(MvcUriComponentsBuilder
                            .fromMethodName(ApplicationController.class, "download", fileName)
                            .build().toString()
                    )
                    .build();
        }).toList();

        return ok(fileInfos);
    }

    @DeleteMapping("/files")
    public ResponseDTO<Void> deleteFiles() {
        fileStorageService.deleteAll();
        return ok();
    }
}
