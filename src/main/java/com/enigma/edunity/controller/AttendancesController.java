package com.enigma.edunity.controller;

import com.enigma.edunity.dto.request.AttendanceRequest;
import com.enigma.edunity.dto.response.AttendanceResponse;
import com.enigma.edunity.dto.response.CommonResponse;
import com.enigma.edunity.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/attendances")
public class AttendancesController {
    private final AttendanceService attendanceService;

    @PreAuthorize("hasRole('ROLE_TUTOR')")
    @PostMapping
    public ResponseEntity<CommonResponse<AttendanceResponse>> createAttendance(
            @RequestBody AttendanceRequest request
    ) {
        AttendanceResponse attendance = attendanceService.createAttendance(request);
        CommonResponse<AttendanceResponse> response = CommonResponse
                .<AttendanceResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create attendance")
                .data(attendance)
                .paging(null)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
