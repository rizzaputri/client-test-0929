package com.enigma.edunity.service;

import com.enigma.edunity.dto.response.ApplicationResponse;
import com.enigma.edunity.entity.Application;
import org.springframework.data.domain.Page;

public interface ApplicationService {
    Application getById(String id);
    ApplicationResponse getRequestById(String id);
    Page<ApplicationResponse> getAllRequests(Integer page, Integer size,
                                             String student, String subject,
                                             String day, String time);
    void deleteRequestById(String id);
}
