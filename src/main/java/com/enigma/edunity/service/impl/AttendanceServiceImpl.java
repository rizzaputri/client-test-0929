package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.request.AttendanceRequest;
import com.enigma.edunity.dto.response.*;
import com.enigma.edunity.entity.*;
import com.enigma.edunity.repository.AttendanceRepository;
import com.enigma.edunity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    private final StudentService studentService;
    private final TutorService tutorService;
    private final SubjectService subjectService;
    private final ApplicationService applicationService;
    private final ScoreService scoreService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AttendanceResponse createAttendance(AttendanceRequest request) {
        Tutor tutor = tutorService.getByUsername();
        Application application = applicationService.getById(request.getApplicationId());
        Subject subject = subjectService.getById(application.getSubject().getId());
        Student student = studentService.getById(application.getStudent().getId());
        Attendance attendance = Attendance.builder()
                .date(LocalDate.now())
                .student(student)
                .tutor(tutor)
                .subject(subject)
                .score(new ArrayList<>())
                .build();
        attendanceRepository.saveAndFlush(attendance);

        List<Score> scores = request.getScore().stream().map(
                score -> Score.builder()
                        .attendance(attendance)
                        .score(score.getScore())
                        .build())
                .toList();
        scoreService.createBulk(scores);

        attendance.getScore().addAll(scores);
        student.getAttendances().add(attendance);

        List<ScoreResponse> scoreResponses = scores.stream().map(
                scoreResponse -> ScoreResponse.builder()
                        .score(scoreResponse.getScore())
                        .build())
                .toList();

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .date(attendance.getDate())
                .student(StudentResponse.builder()
                        .studentId(student.getId())
                        .name(student.getName())
                        .location(student.getLocation())
                        .build())
                .tutor(TutorResponse.builder()
                        .tutorId(tutor.getId())
                        .name(tutor.getName())
                        .subjects(tutor.getSubjects().stream().map(
                                sub -> SubjectResponse.builder()
                                        .subjectId(subject.getId())
                                        .subjectName(subject.getSubjectName())
                                        .build()
                        ).toList())
                        .location(tutor.getLocation())
                        .build())
                .score(scoreResponses)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Attendance> getAllAttendancesByStudentIdAndSubjectIdAndMonth(String studentId, String subjectId, Integer month) {
        return attendanceRepository.findAllByStudentIdAndSubjectIdAndMonth(studentId, subjectId, month);
    }
}
