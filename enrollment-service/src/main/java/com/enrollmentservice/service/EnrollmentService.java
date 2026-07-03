package com.enrollmentservice.service;

import com.enrollmentservice.dto.EnrollmentDetailResponse;
import com.enrollmentservice.model.Enrollment;

import java.util.List;

/*
Contrato de negocio para gestionar matriculas transaccionales.
*/
public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    Enrollment getEnrollmentById(Long id);
    EnrollmentDetailResponse getEnrollmentDetailById(Long id);
    List<Enrollment> getEnrollmentsByStudentId(Long studentId);
    Enrollment createEnrollment(Enrollment enrollment);
    Enrollment updateEnrollment(Long id, Enrollment enrollmentDetails);
    void deleteEnrollment(Long id);
}
