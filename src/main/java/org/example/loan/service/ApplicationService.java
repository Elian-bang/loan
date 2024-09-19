package org.example.loan.service;

import org.example.loan.dto.ApplicationDTO.Request;
import org.example.loan.dto.ApplicationDTO.Response;

public interface ApplicationService {

    Response create(Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, Request request);

}
