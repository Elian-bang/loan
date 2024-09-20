package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.JudgmentDTO.Request;
import org.example.loan.dto.JudgmentDTO.Response;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.JudgmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
