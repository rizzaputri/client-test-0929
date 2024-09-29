package com.enigma.edunity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    private String id;
    private LocalDate date;
    private StudentResponse student;
    private TutorResponse tutor;
    private List<ScoreResponse> score;
}
