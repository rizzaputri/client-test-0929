package com.enigma.edunity.dto.response;

import com.enigma.edunity.constant.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private String requestId;
    private StudentResponse student;
    private SubjectResponse subject;
    private Day day;
    private Time time;
}
