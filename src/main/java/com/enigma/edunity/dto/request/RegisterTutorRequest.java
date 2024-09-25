package com.enigma.edunity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTutorRequest {
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^08\\d{9,11}$",
            message = "Phone number must be valid, starts with '08' and followed by 9 to 11 numbers")
    private String phoneNumber;

    @NotNull(message = "Location must not be null")
    private LocationRequest location;
}
