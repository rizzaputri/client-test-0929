package com.enigma.edunity.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequest {
    @NotBlank(message = "Application ID must not be blank")
    private String id;

    private String subjectId;

    @Enumerated(EnumType.STRING)
    private String day;

    private String time;
}
