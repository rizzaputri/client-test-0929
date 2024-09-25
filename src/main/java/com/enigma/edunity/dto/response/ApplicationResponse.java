package com.enigma.edunity.dto.response;

import com.enigma.edunity.constant.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private String applicationId;
    private StudentResponse student;
    private SubjectResponse subject;
    private String tutorId;
    private Day day;
    private LocalTime time;
    private Boolean status;
}
