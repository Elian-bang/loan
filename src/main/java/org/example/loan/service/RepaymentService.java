package org.example.loan.service;


import org.example.loan.dto.RepaymentDTO.ListResponse;
import org.example.loan.dto.RepaymentDTO.Request;
import org.example.loan.dto.RepaymentDTO.Response;

import java.util.List;

public interface RepaymentService {

    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);
}
