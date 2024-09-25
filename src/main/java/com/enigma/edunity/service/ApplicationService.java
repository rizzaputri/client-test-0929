package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.AcceptApplicationRequest;
import com.enigma.edunity.dto.request.CreateApplicationRequest;
import com.enigma.edunity.dto.request.UpdateApplicationRequest;
import com.enigma.edunity.dto.response.ApplicationResponse;
import com.enigma.edunity.entity.Application;
import org.springframework.data.domain.Page;

public interface ApplicationService {
    ApplicationResponse createApplication(CreateApplicationRequest request);
    Application getById(String id);
    ApplicationResponse getApplicationById(String id);
    Page<ApplicationResponse> getAllApplications(Integer page, Integer size,
                                                 String student, String subject,
                                                 String day, String time);
    Page<ApplicationResponse> getAllStudentApplications(Integer page, Integer size,
                                                 String subject, String day, String time);
    Page<ApplicationResponse> getAllTutorApplications(Integer page, Integer size,
                                                      String student, String subject,
                                                      String day, String time);
    ApplicationResponse updateApplication(UpdateApplicationRequest request);
    ApplicationResponse acceptApplication(AcceptApplicationRequest request);
    void deleteApplication(String id);
}
