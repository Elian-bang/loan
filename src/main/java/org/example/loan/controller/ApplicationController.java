package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import static org.example.loan.dto.ApplicationDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;


    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(applicationService.get(applicationId));
    }

}
