package com.enigma.edunity.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentRequest {
    private String username;

    private String password;

    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^08\\d{9,11}$",
            message = "Phone number must be valid, starts with '08' and followed by 9 to 11 numbers")
    private String phoneNumber;

    private LocationRequest location;
}
