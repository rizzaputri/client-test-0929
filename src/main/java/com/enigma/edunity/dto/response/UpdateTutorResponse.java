package com.enigma.edunity.dto.response;

import com.enigma.edunity.dto.request.LocationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTutorResponse {
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private LocationRequest location;
    private List<SubjectResponse> subjects;
}
