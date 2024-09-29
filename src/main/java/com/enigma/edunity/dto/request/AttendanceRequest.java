package com.enigma.edunity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest {
    @NotBlank(message = "Application ID must not be null")
    private String applicationId;

    @NotNull(message = "Score must not be null")
    private List<ScoreRequest> score;
}
