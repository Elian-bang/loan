package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.loan.domain.Counsel;
import org.example.loan.dto.CounselDTO.Request;
import org.example.loan.dto.CounselDTO.Response;
import org.example.loan.repository.CounselRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final CounselRepository counselRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = false)
    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, Response.class);
    }
}
