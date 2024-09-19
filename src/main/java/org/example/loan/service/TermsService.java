package org.example.loan.service;

import org.example.loan.dto.TermsDTO;

import java.util.List;

import static org.example.loan.dto.TermsDTO.*;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
