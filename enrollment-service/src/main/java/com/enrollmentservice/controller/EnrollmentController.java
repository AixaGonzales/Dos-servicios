package com.enrollmentservice.controller;

import com.enrollmentservice.dto.EnrollmentDetailResponse;
import com.enrollmentservice.model.Enrollment;
import com.enrollmentservice.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Controlador REST para exponer endpoints de matriculas.
*/
@RestController
@RequestMapping("/v1/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/list")
    public ResponseEntity<List<Enrollment>> listEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Enrollment> findEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<EnrollmentDetailResponse> findEnrollmentDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentDetailById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> findEnrollmentsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    @PostMapping("/register")
    public ResponseEntity<Enrollment> registerEnrollment(@Valid @RequestBody Enrollment enrollment) {
        Enrollment createdEnrollment = enrollmentService.createEnrollment(enrollment);
        return new ResponseEntity<>(createdEnrollment, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody Enrollment enrollmentDetails) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollmentDetails));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
