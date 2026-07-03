package com.enrollmentservice.service.impl;

import com.enrollmentservice.dto.EnrollmentDetailResponse;
import com.enrollmentservice.dto.StudentResponse;
import com.enrollmentservice.exception.ResourceNotFoundException;
import com.enrollmentservice.model.Enrollment;
import com.enrollmentservice.repository.EnrollmentRepository;
import com.enrollmentservice.service.EnrollmentService;
import com.enrollmentservice.service.StudentClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
Implementacion de la logica transaccional basica de matriculas.
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentClientService studentClientService;

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        log.info("Invocando listado de matriculas");
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(Long id) {
        log.info("Buscando matricula por ID: {}", id);
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matricula no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentDetailResponse getEnrollmentDetailById(Long id) {
        Enrollment enrollment = getEnrollmentById(id);
        StudentResponse student = studentClientService.getStudentById(enrollment.getStudentId());
        return new EnrollmentDetailResponse(enrollment, student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        studentClientService.getStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public Enrollment createEnrollment(Enrollment enrollment) {
        log.info("Registrando matricula para estudiante ID: {}", enrollment.getStudentId());
        studentClientService.getStudentById(enrollment.getStudentId());
        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public Enrollment updateEnrollment(Long id, Enrollment enrollmentDetails) {
        log.info("Actualizando matricula con ID: {}", id);
        Enrollment enrollment = getEnrollmentById(id);
        studentClientService.getStudentById(enrollmentDetails.getStudentId());

        enrollment.setStudentId(enrollmentDetails.getStudentId());
        enrollment.setCourseName(enrollmentDetails.getCourseName());
        enrollment.setEnrollmentDate(enrollmentDetails.getEnrollmentDate());
        enrollment.setStatus(enrollmentDetails.getStatus());

        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void deleteEnrollment(Long id) {
        log.info("Eliminando matricula con ID: {}", id);
        Enrollment enrollment = getEnrollmentById(id);
        enrollmentRepository.delete(enrollment);
    }
}
