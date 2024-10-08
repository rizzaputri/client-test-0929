package com.enigma.edunity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptApplicationRequest {
    @NotBlank(message = "Application ID must not be null")
    private String applicationId;
}
