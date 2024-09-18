package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.CounselDTO;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.CounselService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstractController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<CounselDTO.Response> create(@RequestBody CounselDTO.Request request) {
        return ok(counselService.create(request));
    }

    @GetMapping("/{counselId}")
    public ResponseDTO<CounselDTO.Response> get(@PathVariable Long counselId) {
        return ok(counselService.get(counselId));
    }


    @PutMapping("/{counselId}")
    public ResponseDTO<CounselDTO.Response> get(@PathVariable Long counselId, @RequestBody CounselDTO.Request request) {
        return ok(counselService.update(counselId, request));
    }


}
