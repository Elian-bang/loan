package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.JudgmentDTO.Request;
import org.example.loan.dto.JudgmentDTO.Response;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.JudgmentService;
import org.springframework.web.bind.annotation.*;

import static org.example.loan.dto.ResponseDTO.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/judgments")
public class JudgmentController {

    private final JudgmentService judgmentService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(judgmentService.create(request));
    }

    @GetMapping("/{judgmentId}")
    public ResponseDTO<Response> get(@PathVariable Long judgmentId) {
        return ok(judgmentService.get(judgmentId));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDTO<Response> getJudgmentOfApplication(@PathVariable Long applicationId) {
        return ok(judgmentService.getJudgmentOfApplication(applicationId));
    }

}
