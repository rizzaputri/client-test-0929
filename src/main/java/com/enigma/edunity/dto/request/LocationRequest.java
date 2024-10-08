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
public class LocationRequest {
    @NotBlank(message = "City must not be null")
    @Enumerated(EnumType.STRING)
    private String city;

    @NotBlank(message = "Province must not be null")
    @Enumerated(EnumType.STRING)
    private String province;
}
