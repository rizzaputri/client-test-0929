package com.enigma.edunity.controller;

import com.enigma.edunity.dto.response.ApplicationResponse;
import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.dto.response.PagingResponse;
import com.enigma.edunity.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TUTOR', 'ROLE_STUDENT')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<ApplicationResponse>> getApplicationById(
            @PathVariable String id
    ) {
        System.out.println("===" + id + "===");
        ApplicationResponse application = applicationService.getApplicationById(id);
        CommonResponse<ApplicationResponse> response = CommonResponse
                .<ApplicationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch application")
                .data(application)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TUTOR')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<ApplicationResponse>>> getAllApplications(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "student", required = false) String student,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "day", required = false) String day,
            @RequestParam(name = "time", required = false) String time
    ) {
        System.out.println("page: " + page + ", size: " + size);
        Page<ApplicationResponse> pagedApplications = applicationService.getAllApplications(
                page, size, student, subject, day, time);

        CommonResponse<List<ApplicationResponse>> response = CommonResponse
                .<List<ApplicationResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch all applications")
                .data(pagedApplications.getContent())
                .paging(PagingResponse.builder()
                        .totalPages(pagedApplications.getTotalPages())
                        .totalElements(pagedApplications.getTotalElements())
                        .page(pagedApplications.getNumber())
                        .size(pagedApplications.getSize())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<?>> deleteApplication(
            @PathVariable String id
    ) {
        System.out.println("===" + id + "===");
        ApplicationResponse application = applicationService.getApplicationById(id);
        String studentName = application.getStudent().getName();

        applicationService.deleteApplication(id);
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete data application of " + studentName)
                .data(application)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
