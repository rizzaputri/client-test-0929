package com.enigma.edunity.service.impl;

import com.enigma.edunity.entity.Score;
import com.enigma.edunity.repository.ScoreRepository;
import com.enigma.edunity.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBulk(List<Score> scores) {
        scoreRepository.saveAllAndFlush(scores);
    }

    @Transactional(readOnly = true)
    @Override
    public Double getAverageByStudentIdAndSubjectIdAndMonth(String studentId, String subjectId, Integer month) {
        System.out.println(studentId + " " + subjectId + " " + month);
        System.out.println(scoreRepository.findAverageByStudentIdAndSubjectIdAndMonth(studentId, subjectId, month));
        return scoreRepository.findAverageByStudentIdAndSubjectIdAndMonth(studentId, subjectId, month);
    }
}
