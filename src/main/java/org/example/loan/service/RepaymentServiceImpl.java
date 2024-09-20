package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.domain.Application;
import org.example.loan.domain.Entry;
import org.example.loan.domain.Repayment;
import org.example.loan.dto.BalanceDTO;
import org.example.loan.dto.BalanceDTO.RepaymentRequest.RepaymentType;
import org.example.loan.dto.RepaymentDTO.Request;
import org.example.loan.dto.RepaymentDTO.Response;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.example.loan.repository.ApplicationRepository;
import org.example.loan.repository.EntryRepository;
import org.example.loan.repository.RepaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final ApplicationRepository applicationRepository;
    private final EntryRepository entryRepository;
    private final BalanceService balanceService;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        // validation
        // 1. 계약을 완료한 신청 정보
        // 2. 집행이 되어있어야 함
        if(!isRepayableApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);
        repaymentRepository.save(repayment);

        BalanceDTO.Response updatedBalance = balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(request.getRepaymentAmount())
                        .type(RepaymentType.REMOVE)
                        .build());

        Response response = modelMapper.map(repayment, Response.class);
        response.setBalance(updatedBalance.getBalance());

        return response;
    }

    private boolean isRepayableApplication(Long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);

        if(application.isEmpty()) {
            return false;
        }else if(application.get().getContractedAt() == null) {
            return false;
        }

        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);
        return entry.isPresent();
    }
}
