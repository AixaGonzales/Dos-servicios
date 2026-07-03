package com.enrollmentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

/*
Paquete model

Esta capa contiene las entidades de dominio de la aplicación que representan
las tablas en la base de datos de matrículas.
*/

/*
Entidad Enrollment

Representa la matrícula de un estudiante en un curso determinado.
*/
@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del estudiante es obligatorio")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 100, message = "El nombre del curso no debe exceder los 100 caracteres")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false)
    private String status; // Ej: ACTIVE, COMPLETED, CANCELLED
}
