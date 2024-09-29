package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.AttendanceRequest;
import com.enigma.edunity.dto.response.AttendanceResponse;
import com.enigma.edunity.entity.Attendance;

import java.util.List;

public interface AttendanceService {
    AttendanceResponse createAttendance(AttendanceRequest request);
    List<Attendance> getAllAttendancesByStudentIdAndSubjectIdAndMonth(String studentId, String subjectId, Integer month);
}
