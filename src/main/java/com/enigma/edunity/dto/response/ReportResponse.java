package com.enigma.edunity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String id;
    private String month;
    private String grade;
    private String studentId;
    private String subjectName;
}
