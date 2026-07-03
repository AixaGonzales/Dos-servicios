package com.enrollmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Clase principal del microservicio de matriculas.
Inicializa Spring Boot y escanea los paquetes internos del proyecto.
*/
@SpringBootApplication
public class EnrollmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrollmentServiceApplication.class, args);
    }
}
