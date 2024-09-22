package com.enigma.edunity.controller;

import com.enigma.edunity.dto.request.LoginRequest;
import com.enigma.edunity.dto.request.RegisterStudentRequest;
import com.enigma.edunity.dto.request.RegisterTutorRequest;
import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.dto.response.LoginResponse;
import com.enigma.edunity.dto.response.RegisterStudentResponse;
import com.enigma.edunity.dto.response.RegisterTutorResponse;
import com.enigma.edunity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/api/v1/student-register")
    public ResponseEntity<CommonResponse<RegisterStudentResponse>> registerStudent(
            @RequestBody RegisterStudentRequest request
    ) {
        RegisterStudentResponse student = authService.registerStudent(request);
        CommonResponse<RegisterStudentResponse> response = CommonResponse
                .<RegisterStudentResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registered student")
                .data(student)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/api/v1/tutor-register")
    public ResponseEntity<CommonResponse<RegisterTutorResponse>> registerTutor(
            @RequestBody RegisterTutorRequest request
    ) {
        RegisterTutorResponse tutor = authService.registerTutor(request);
        CommonResponse<RegisterTutorResponse> response = CommonResponse
                .<RegisterTutorResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registered tutor")
                .data(tutor)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/api/v1/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody LoginRequest request){
        LoginResponse login = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully login")
                .data(login)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
