package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.UpdateTutorRequest;
import com.enigma.edunity.dto.response.TutorResponse;
import com.enigma.edunity.dto.response.UpdateTutorResponse;
import com.enigma.edunity.entity.Tutor;
import org.springframework.data.domain.Page;

public interface TutorService {
    void registerTutor(Tutor tutor);
    Tutor getById(String id);
    Tutor getByUsername();
    TutorResponse getTutorById(String id);
    Page<TutorResponse> getAllTutors(Integer page, Integer size,
                                       String name, String subject, String location);
    UpdateTutorResponse updateTutor(UpdateTutorRequest request);
    void deleteTutor(String id);
}
