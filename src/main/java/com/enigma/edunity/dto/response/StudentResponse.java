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
public class StudentResponse {
    private String studentId;
    private String name;
    private Location location;
}
