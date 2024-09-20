package org.example.loan.service;

import org.example.loan.dto.BalanceDTO.CreateRequest;
import org.example.loan.dto.BalanceDTO.Response;

public interface BalanceService {

    Response create(Long applicationId, CreateRequest request);
}
