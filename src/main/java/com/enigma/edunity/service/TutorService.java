package com.enigma.edunity.service;

import com.enigma.edunity.dto.response.TutorResponse;
import com.enigma.edunity.entity.Tutor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TutorService {
    void registerTutor(Tutor tutor);
    Tutor getById(String id);
    TutorResponse getTutorById(String id);
    Page<TutorResponse> getAllTutors(Integer page, Integer size,
                                       String name, String subject, String location);
    void deleteTutor(String id);
}
