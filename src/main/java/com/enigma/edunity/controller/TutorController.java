package com.enigma.edunity.controller;

import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.dto.response.PagingResponse;
import com.enigma.edunity.dto.response.TutorResponse;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<TutorResponse>> getTutorById(
            @PathVariable String id
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
                        .build())
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
}
