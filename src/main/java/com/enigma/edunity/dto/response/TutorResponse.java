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
public class TutorResponse {
    private String tutorId;
    private String name;
    private List<SubjectResponse> subjects;
    private Location location;
}
