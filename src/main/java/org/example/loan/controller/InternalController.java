package org.example.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.loan.dto.EntryDTO.Request;
import org.example.loan.dto.EntryDTO.Response;
import org.example.loan.dto.EntryDTO.UpdateResponse;
import org.example.loan.dto.RepaymentDTO;
import org.example.loan.dto.ResponseDTO;
import org.example.loan.service.EntryService;
import org.example.loan.service.RepaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

    private final RepaymentService repaymentService;

    @PostMapping("/{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }

    @GetMapping("/{applicationId}/entries")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(entryService.get(applicationId));
    }

    @PutMapping("/entries/{entryId}")
    public ResponseDTO<UpdateResponse> update(@PathVariable Long entryId, @RequestBody Request request) {
        return ok(entryService.update(entryId, request));
    }

    @DeleteMapping("/entries/{entryId}")
    public ResponseDTO<Void> delete(@PathVariable Long entryId) {
        entryService.delete(entryId);
        return ok();
    }

    @PostMapping("{applicationId}/repayments")
    public ResponseDTO<RepaymentDTO.Response> create(@PathVariable Long applicationId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.create(applicationId, request));
    }

}
