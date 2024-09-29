package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.request.CreateSubjectRequest;
import com.enigma.edunity.dto.request.UpdateSubjectRequest;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.entity.Subject;
import com.enigma.edunity.repository.SubjectRepository;
import com.enigma.edunity.service.SubjectService;
import com.enigma.edunity.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubjectResponse createSubject(CreateSubjectRequest request) {
        validationUtil.validate(request);
        Subject subject = Subject.builder().subjectName(request.getSubjectName())
                .build();
        Subject savedSubject = subjectRepository.saveAndFlush(subject);
        return ResponseBuilder(savedSubject);
    }

    @Transactional(readOnly = true)
    @Override
    public Subject getById(String id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public SubjectResponse getSubjectById(String id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found")
        );
        return ResponseBuilder(subject);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Subject> getAllSubjectsByStudentId(String studentId) {
        return subjectRepository.findAllByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SubjectResponse> getAllSubjects(Integer page, Integer size, String name) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Subject> subjects;
        if (name != null && !name.isEmpty()) {
            subjects = subjectRepository.findBySubjectNameLike(pageable, "%" + name + "%");
        } else {
            subjects = subjectRepository.findAll(pageable);
        }

        return subjects.map(this::ResponseBuilder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubjectResponse updateSubject(UpdateSubjectRequest request) {
        validationUtil.validate(request);
        Subject subject = getById(request.getId());
        subject.setSubjectName(request.getSubjectName());
        return ResponseBuilder(subject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSubject(String id) {
        subjectRepository.deleteById(id);
    }

    private SubjectResponse ResponseBuilder(Subject subject) {
        return SubjectResponse.builder()
                .subjectId(subject.getId())
                .subjectName(subject.getSubjectName())
                .build();
    }
}
