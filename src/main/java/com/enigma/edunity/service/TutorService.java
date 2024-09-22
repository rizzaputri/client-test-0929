package com.enigma.edunity.service;

import com.enigma.edunity.entity.Tutor;

import java.util.Optional;

public interface TutorService {
    void registerTutor(Tutor tutor);
    Optional<String> findTutorEmail(String email);
    String findTutorPhoneNumber(String phoneNumber);
}
