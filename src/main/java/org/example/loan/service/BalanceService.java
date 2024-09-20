package org.example.loan.service;

import org.example.loan.dto.BalanceDTO.CreateRequest;
import org.example.loan.dto.BalanceDTO.Response;
import org.example.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, CreateRequest request);

    Response update(Long applicationId, UpdateRequest request);
}
