package com.enigma.edunity.service.impl;

import com.enigma.edunity.dto.response.ReportResponse;
import com.enigma.edunity.entity.Attendance;
import com.enigma.edunity.entity.Report;
import com.enigma.edunity.entity.Student;
import com.enigma.edunity.entity.Subject;
import com.enigma.edunity.repository.ReportRepository;
import com.enigma.edunity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    private final SubjectService subjectService;
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final ScoreService scoreService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<ReportResponse> getReports(Integer page, Integer size) {
        Student student = studentService.getByUsername();
        List<Subject> subjects = subjectService.getAllSubjectsByStudentId(student.getId());
        System.out.println("Student's subjects: ");
        subjects.forEach(System.out::println);

        List<Report> reports = new ArrayList<>();

        for (Subject subject : subjects) {
            System.out.println("Student ID : ");
            System.out.println(student.getId());
            System.out.println("Subject ID : ");
            System.out.println(subject.getId());
            Page<Report> studentReports = getAllStudentReportsBySubject(page, size, student.getId(), subject.getId());
            System.out.println("Student's reports: ");
            studentReports.getContent().forEach(System.out::println);
            reports.addAll(studentReports.getContent());

            if (studentReports.isEmpty()) {
                List<Attendance> attendances = attendanceService.getAllAttendancesByStudentIdAndSubjectIdAndMonth(student.getId(),
                        subject.getId(), LocalDate.now().minusMonths(1).getMonthValue());
                System.out.println("Attendances: ");
                attendances.forEach(System.out::println);

                if (!attendances.isEmpty()) {
                    System.out.println("Condition: reports are empty & attendances are not empty");
                    Double grade = scoreService.getAverageByStudentIdAndSubjectIdAndMonth(student.getId(), subject.getId(),
                            LocalDate.now().minusMonths(1).getMonthValue());
                    System.out.println("Calculated Grade: " + grade);

                    Report newReport = Report.builder()
                            .month(LocalDate.now().minusMonths(1).getMonth().toString())
                            .grade(grade.toString())
                            .student(student)
                            .subject(subject)
                            .build();
                    reportRepository.saveAndFlush(newReport);
                    reports.add(newReport);
                } else {
                    System.out.println("Condition: reports are empty & attendances are empty");
                    continue;
                }
            } else {
                System.out.println("Condition: reports are not empty");
                Page<Report> studentReportsByMonth = getAllStudentReportsBySubjectAndMonth(page, size, student.getId(),
                        subject.getId(), LocalDate.now().minusMonths(1).getMonth().toString());
                System.out.println("Reports by Month: ");
                studentReportsByMonth.getContent().forEach(System.out::println);

                if (studentReportsByMonth.isEmpty()) {
                    System.out.println("Condition: reports by month are empty");
                    Double grade = scoreService.getAverageByStudentIdAndSubjectIdAndMonth(student.getId(), subject.getId(),
                            LocalDate.now().minusMonths(1).getMonthValue());
                    System.out.println("Calculated Grade: " + grade);

                    Report newReport = Report.builder()
                            .month(LocalDate.now().minusMonths(1).getMonth().toString())
                            .grade(grade.toString())
                            .student(student)
                            .subject(subject)
                            .build();
                    reportRepository.saveAndFlush(newReport);
                    reports.add(newReport);
                } else {
                    System.out.println("Condition: reports are not empty & reports for last month are not empty");
                    continue;
                }
            }
        }

        if (reports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report is not available yet");
        }

        List<ReportResponse> reportResponses = reports.stream()
                .filter(Objects::nonNull)
                .map(newReport -> ReportResponse.builder()
                        .id(newReport.getId())
                        .month(newReport.getMonth())
                        .grade(newReport.getGrade())
                        .studentId(newReport.getStudent().getId())
                        .subjectName(newReport.getSubject().getSubjectName())
                        .build())
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(reportResponses, pageable, reportResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Report> getAllStudentReports(Integer page, Integer size, String id) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findAllByStudentId(id, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Report> getAllStudentReportsByMonth(String id, String month) {
        return reportRepository.findAllByStudentIdAndMonth(id, month);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Report> getAllStudentReportsBySubject(Integer page, Integer size, String studentId, String subjectId) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findAllByStudentIdAndSubjectId(studentId, subjectId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Report> getAllStudentReportsBySubjectAndMonth(Integer page, Integer size, String studentId,
                                                              String subjectId, String month
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findAllByStudentIdAndSubjectIdAndMonth(studentId, subjectId, month, pageable);
    }
}
