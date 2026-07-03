package com.enrollmentservice.service;

import com.enrollmentservice.dto.StudentResponse;

/*
Contrato para consultar datos maestros del estudiante desde student-service.
*/
public interface StudentClientService {
    StudentResponse getStudentById(Long studentId);
}
