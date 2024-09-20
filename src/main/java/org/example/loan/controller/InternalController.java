package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.EntryDTO.Request;
import org.example.loan.dto.EntryDTO.Response;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.EntryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

    @PostMapping("{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }
}
