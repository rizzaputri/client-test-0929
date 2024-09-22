package com.enigma.edunity.service.impl;

import com.enigma.edunity.entity.Tutor;
import com.enigma.edunity.repository.TutorRepository;
import com.enigma.edunity.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final TutorRepository tutorRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerTutor(Tutor tutor) {
        tutorRepository.saveAndFlush(tutor);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<String> findTutorEmail(String email) {
        return tutorRepository.findEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public String findTutorPhoneNumber(String phoneNumber) {
        return tutorRepository.findPhoneNumber(phoneNumber);
    }
}
