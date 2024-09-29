package com.enigma.edunity.service;

import com.enigma.edunity.entity.Score;

import java.util.List;

public interface ScoreService {
    void createBulk(List<Score> scores);
    Double getAverageByStudentIdAndSubjectIdAndMonth(String studentId, String subjectId, Integer month);
}
