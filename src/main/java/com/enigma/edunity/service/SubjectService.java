package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.CreateSubjectRequest;
import com.enigma.edunity.dto.request.UpdateSubjectRequest;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectService {
    SubjectResponse createSubject(CreateSubjectRequest request);
    Subject getById(String id);
    SubjectResponse getSubjectById(String id);
    List<Subject> getAllSubjects();
    List<Subject> getAllSubjectsByStudentId(String studentId);
    Page<SubjectResponse> getAllSubjects(Integer page, Integer size, String name);
    SubjectResponse updateSubject(UpdateSubjectRequest request);
    void deleteSubject(String id);
}
