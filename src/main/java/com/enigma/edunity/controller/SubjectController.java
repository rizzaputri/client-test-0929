package com.enigma.edunity.controller;

import com.enigma.edunity.dto.request.CreateSubjectRequest;
import com.enigma.edunity.dto.request.UpdateSubjectRequest;
import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.dto.response.PagingResponse;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.entity.Subject;
import com.enigma.edunity.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<SubjectResponse>> createSubject(
            @RequestBody CreateSubjectRequest request
    ) {
        SubjectResponse subject = subjectService.createSubject(request);
        CommonResponse<SubjectResponse> response = CommonResponse
                .<SubjectResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create subject")
                .data(subject)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<SubjectResponse>> getSubjectById(
            @PathVariable String id
    ) {
        SubjectResponse subject = subjectService.getSubjectById(id);
        CommonResponse<SubjectResponse> response = CommonResponse
                .<SubjectResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch subject of " + subject.getSubjectName())
                .data(subject)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SubjectResponse>>> getAllSubjects(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name
    ) {
        Page<SubjectResponse> pagedSubjects = subjectService.getAllSubjects(page, size, name);

        CommonResponse<List<SubjectResponse>> response = CommonResponse
                .<List<SubjectResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully fetch all subjects")
                .data(pagedSubjects.getContent())
                .paging(PagingResponse.builder()
                        .totalPages(pagedSubjects.getTotalPages())
                        .totalElements(pagedSubjects.getTotalElements())
                        .page(pagedSubjects.getNumber())
                        .size(pagedSubjects.getSize())
                        .hasNext(pagedSubjects.hasNext())
                        .hasPrevious(pagedSubjects.hasPrevious())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<CommonResponse<SubjectResponse>> updateSubject(
            @RequestBody UpdateSubjectRequest request
    ) {
        String subjectName = subjectService.getById(request.getId()).getSubjectName();
        SubjectResponse subject = subjectService.updateSubject(request);
        CommonResponse<SubjectResponse> response = CommonResponse
                .<SubjectResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully update subject of " + subjectName)
                .data(subject)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<?>> deleteSubject(
            @PathVariable String id
    ) {
        SubjectResponse subject = subjectService.getSubjectById(id);
        String subjectName = subject.getSubjectName();

        subjectService.deleteSubject(id);
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete subject of " + subjectName)
                .data(subject)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
