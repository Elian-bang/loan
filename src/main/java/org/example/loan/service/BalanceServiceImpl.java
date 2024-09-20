package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.domain.Balance;
import org.example.loan.dto.BalanceDTO.CreateRequest;
import org.example.loan.dto.BalanceDTO.RepaymentRequest;
import org.example.loan.dto.BalanceDTO.RepaymentRequest.RepaymentType;
import org.example.loan.dto.BalanceDTO.Response;
import org.example.loan.dto.BalanceDTO.UpdateRequest;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.example.loan.repository.BalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, CreateRequest request) {
        Balance balance = modelMapper.map(request, Balance.class);

        BigDecimal entryAmount = request.getEntryAmount();
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        balanceRepository.findByApplicationId(applicationId).ifPresent(b -> {
            balance.setBalanceId(b.getBalanceId());
            balance.setIsDeleted(b.getIsDeleted());
            balance.setCreatedAt(b.getCreatedAt());
            balance.setCreatedAt(b.getUpdatedAt());
        });

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Balance balance = balanceRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(balance, Response.class);
    }

    @Override
    public Response repaymentUpdate(Long applicationId, RepaymentRequest request) {
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal updatedBalance = balance.getBalance();
        BigDecimal repaymentAmount = request.getRepaymentAmount();

        if (request.getType().equals(RepaymentType.ADD)) {
            updatedBalance = updatedBalance.add(repaymentAmount);
        } else {
            updatedBalance = updatedBalance.subtract(repaymentAmount);

            if(updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new BaseException(ResultType.SYSTEM_ERROR);
            }
        }

        balance.setBalance(updatedBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Balance balance = balanceRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        balance.setIsDeleted(true);

        balanceRepository.save(balance);
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {
        // balance
        Balance balance = balanceRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        // change
        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getAfterEntryAmount();
        BigDecimal updatedBalance = balance.getBalance();

        updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updatedBalance);
        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, Response.class);
    }
}
