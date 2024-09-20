package org.example.loan.service;


import org.example.loan.dto.RepaymentDTO.Request;
import org.example.loan.dto.RepaymentDTO.Response;

public interface RepaymentService {

    Response create(Long applicationId, Request request);
}
