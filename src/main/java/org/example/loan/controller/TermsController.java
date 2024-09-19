package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.dto.TermsDTO.*;
import org.example.loan.service.TermsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping()
    public ResponseDTO<List<Response>> getAll() {
        return ok(termsService.getAll());
    }
}

