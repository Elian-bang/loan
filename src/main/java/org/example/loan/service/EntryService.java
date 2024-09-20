package org.example.loan.service;

import org.example.loan.dto.EntryDTO.Request;
import org.example.loan.dto.EntryDTO.Response;

public interface EntryService {

    Response create(Long applicationId, Request object);

    Response get(Long applicationId);

}
