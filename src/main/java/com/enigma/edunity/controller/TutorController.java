package com.enigma.edunity.controller;

import com.enigma.edunity.dto.request.AcceptApplicationRequest;
import com.enigma.edunity.dto.request.UpdateTutorRequest;
import com.enigma.edunity.dto.response.*;
import com.enigma.edunity.service.ApplicationService;
import com.enigma.edunity.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tutors")
public class TutorController {
    private final TutorService tutorService;
    private final ApplicationService applicationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TUTOR')")
    @GetMapping({"/{id}", "/profiles"})
    public ResponseEntity<CommonResponse<TutorResponse>> getTutorById(
            @PathVariable(required = false) String id
    ) {
        TutorResponse tutor = tutorService.getTutorById(id);
        CommonResponse<TutorResponse> response = CommonResponse
                .<TutorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch data of " + tutor.getName())
                .data(tutor)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TutorResponse>>> getAllTutors(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "city", required = false) String city
    ) {
        Page<TutorResponse> pagedTutors = tutorService.getAllTutors(page, size, name, subject, city);

        CommonResponse<List<TutorResponse>> response = CommonResponse
                .<List<TutorResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch all tutors")
                .data(pagedTutors.getContent())
                .paging(PagingResponse.builder()
                        .totalPages(pagedTutors.getTotalPages())
                        .totalElements(pagedTutors.getTotalElements())
                        .page(pagedTutors.getNumber())
                        .size(pagedTutors.getSize())
                        .hasNext(pagedTutors.hasNext())
                        .hasPrevious(pagedTutors.hasPrevious())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_TUTOR')")
    @PutMapping(path = "/profiles")
    public ResponseEntity<CommonResponse<UpdateTutorResponse>> updateTutor(
            @RequestBody UpdateTutorRequest request
    ) {
        UpdateTutorResponse tutor = tutorService.updateTutor(request);
        CommonResponse<UpdateTutorResponse> response = CommonResponse
                .<UpdateTutorResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully update data of " + tutor.getName())
                .data(tutor)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<?>> deleteTutorById(
            @PathVariable String id
    ) {
        TutorResponse tutor = tutorService.getTutorById(id);
        String tutorName = tutor.getName();

        tutorService.deleteTutor(id);
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete data of " + tutorName)
                .data(tutor)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_TUTOR')")
    @PutMapping(path = "/applications")
    public ResponseEntity<CommonResponse<ApplicationResponse>> acceptApplication(
            @RequestBody AcceptApplicationRequest request
    ) {
        System.out.println("===" + request.getApplicationId() + "===");
        ApplicationResponse application = applicationService.acceptApplication(request);
        CommonResponse<ApplicationResponse> response = CommonResponse
                .<ApplicationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully accept application")
                .data(application)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_TUTOR')")
    @GetMapping(path = "/applications")
    public ResponseEntity<CommonResponse<List<ApplicationResponse>>> getAllTutorApplications(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "student", required = false) String student,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "day", required = false) String day,
            @RequestParam(name = "time", required = false) String time
    ) {
        Page<ApplicationResponse> pagedApplications = applicationService.getAllTutorApplications(
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
                        .hasNext(pagedApplications.hasNext())
                        .hasPrevious(pagedApplications.hasPrevious())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
