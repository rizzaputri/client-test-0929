package com.enigma.edunity.dto.response;

import com.enigma.edunity.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTutorResponse {
    private String tutorId;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;
    private List<String> roles;
}
