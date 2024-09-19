package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.domain.Application;
import org.example.loan.dto.ApplicationDTO;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.example.loan.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public ApplicationDTO.Response create(ApplicationDTO.Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        application.setIsDeleted(true);

        applicationRepository.save(application);
    }

}
