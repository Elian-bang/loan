package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.domain.Application;
import org.example.loan.domain.Entry;
import org.example.loan.dto.BalanceDTO;
import org.example.loan.dto.EntryDTO.Request;
import org.example.loan.dto.EntryDTO.Response;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.example.loan.repository.ApplicationRepository;
import org.example.loan.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final BalanceService balanceService;
    private final EntryRepository entryRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        // 계약 체결 여부 검증
        if(!isContractedApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 집행
        Entry entry = modelMapper.map(request, Entry.class);
        entry.setApplicationId(applicationId);
        entryRepository.save(entry);

        // 대출 잔고 관리
        balanceService.create(applicationId,
                BalanceDTO.CreateRequest.builder()
                .entryAmount(request.getEntryAmount())
                .build());

        return modelMapper.map(entry, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);

        if(entry.isPresent()) {
            return modelMapper.map(entry, Response.class);
        }else {
            return null;
        }
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> existed = applicationRepository.findById(applicationId);
        if(existed.isEmpty()) {
            return false;
        }

        return existed.get().getContractedAt() != null;
    }
}
