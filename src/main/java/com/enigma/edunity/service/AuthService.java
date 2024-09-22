package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.LoginRequest;
import com.enigma.edunity.dto.request.RegisterStudentRequest;
import com.enigma.edunity.dto.request.RegisterTutorRequest;
import com.enigma.edunity.dto.response.LoginResponse;
import com.enigma.edunity.dto.response.RegisterStudentResponse;
import com.enigma.edunity.dto.response.RegisterTutorResponse;

public interface AuthService {
    RegisterStudentResponse registerStudent(RegisterStudentRequest registerStudentRequest);
    RegisterTutorResponse registerTutor(RegisterTutorRequest registerTutorRequest);
    LoginResponse login(LoginRequest loginRequest);
}
