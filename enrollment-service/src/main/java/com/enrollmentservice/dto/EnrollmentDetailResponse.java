package com.enrollmentservice.dto;

import com.enrollmentservice.model.Enrollment;

/*
DTO compuesto para devolver una matricula junto con los datos del estudiante.
*/
public record EnrollmentDetailResponse(
        Enrollment enrollment,
        StudentResponse student
) {
}
