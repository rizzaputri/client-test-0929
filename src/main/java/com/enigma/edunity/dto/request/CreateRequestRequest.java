package com.enigma.edunity.dto.request;

import com.enigma.edunity.constant.Day;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestRequest {
    @NotBlank(message = "Subject ID must not be blank")
    private String subjectId;

    @NotNull(message = "Day must not be blank")
    @Enumerated(EnumType.STRING)
    private Day day;

    @NotNull(message = "Time must not be blank")
    private Time time;
}
