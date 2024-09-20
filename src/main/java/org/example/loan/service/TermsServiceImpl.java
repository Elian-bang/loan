package org.example.loan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.loan.domain.Terms;
import org.example.loan.repository.TermsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.loan.dto.TermsDTO.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Terms terms = modelMapper.map(request, Terms.class);
        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, Response.class);
    }

    @Override
    public List<Response> getAll() {
        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream()
                .map(term -> modelMapper.map(term, Response.class))
                .collect(Collectors.toList());
    }
}
