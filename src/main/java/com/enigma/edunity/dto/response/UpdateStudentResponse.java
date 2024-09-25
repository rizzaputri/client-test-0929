package com.enigma.edunity.dto.response;

import com.enigma.edunity.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentResponse {
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;
}
