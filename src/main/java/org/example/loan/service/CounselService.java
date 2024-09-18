package org.example.loan.service;

import static org.example.loan.dto.CounselDTO.*;

public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);
    
}
