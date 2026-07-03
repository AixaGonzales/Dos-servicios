package com.enrollmentservice.dto;

import java.time.LocalDate;

/*
DTO que representa la respuesta basica recibida desde student-service.
*/
public record StudentResponse(
        Long id,
        String dni,
        String firstName,
        String lastName,
        String promotion,
        LocalDate date
) {
}
