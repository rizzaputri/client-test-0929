package com.enigma.edunity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubjectRequest {
    @NotBlank(message = "Subject ID must not be blank")
    private String id;

    @NotBlank(message = "Subject name must not be blank")
    @Pattern(regexp = "^[A-Z].*")
    private String subjectName;
}
