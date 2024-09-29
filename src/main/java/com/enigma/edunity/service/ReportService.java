package com.enigma.edunity.service;

import com.enigma.edunity.dto.response.ReportResponse;
import com.enigma.edunity.entity.Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportService {
    Page<ReportResponse> getReports(Integer page, Integer size);
    List<Report> getAllStudentReports(Integer page, Integer size, String id);
    List<Report> getAllStudentReportsByMonth(String id, String month);

    Page<Report> getAllStudentReportsBySubject(Integer page, Integer size, String studentId, String subjectId);
    Page<Report> getAllStudentReportsBySubjectAndMonth(Integer page, Integer size, String studentId, String subjectId, String month);
}
