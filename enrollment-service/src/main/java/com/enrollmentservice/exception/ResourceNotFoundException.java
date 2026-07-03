package com.enrollmentservice.exception;

/*
Excepcion de negocio para recursos no encontrados.
*/
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
