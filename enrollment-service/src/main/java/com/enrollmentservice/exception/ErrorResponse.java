package com.enrollmentservice.exception;

import java.time.LocalDateTime;

/*
Respuesta JSON estandar para errores de la API.
*/
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
