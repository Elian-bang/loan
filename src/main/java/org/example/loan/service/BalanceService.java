package org.example.loan.service;

import org.example.loan.dto.BalanceDTO.CreateRequest;
import org.example.loan.dto.BalanceDTO.RepaymentRequest;
import org.example.loan.dto.BalanceDTO.Response;
import org.example.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, CreateRequest request);

    Response update(Long applicationId, UpdateRequest request);

    Response get(Long applicationId);

    Response repaymentUpdate(Long applicationId, RepaymentRequest request);

    void delete(Long applicationId);

}
