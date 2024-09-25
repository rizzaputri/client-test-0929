package com.enigma.edunity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTutorRequest {
    private String username;

    private String password;

    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^08\\d{9,11}$",
            message = "Phone number must be valid, starts with '08' and followed by 9 to 11 numbers")
    private String phoneNumber;

    private LocationRequest location;

    private List<String> subjectIds;
}
