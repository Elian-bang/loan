package org.example.loan.service;

import org.example.loan.dto.EntryDTO.Request;
import org.example.loan.dto.EntryDTO.Response;
import org.example.loan.dto.EntryDTO.UpdateResponse;

public interface EntryService {

    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    UpdateResponse update(Long entryId, Request request);

    void delete(Long entryId);

}
