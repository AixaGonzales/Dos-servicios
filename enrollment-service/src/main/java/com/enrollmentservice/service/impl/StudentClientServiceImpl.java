package com.enrollmentservice.service.impl;

import com.enrollmentservice.dto.StudentResponse;
import com.enrollmentservice.exception.ResourceNotFoundException;
import com.enrollmentservice.service.StudentClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

/*
Cliente HTTP que consume el microservicio maestro student-service.
*/
@Service
@Slf4j
public class StudentClientServiceImpl implements StudentClientService {

    private final RestClient restClient;

    public StudentClientServiceImpl(
            RestClient.Builder restClientBuilder,
            @Value("${services.student.url}") String studentServiceUrl) {
        this.restClient = restClientBuilder.baseUrl(studentServiceUrl).build();
    }

    @Override
    public StudentResponse getStudentById(Long studentId) {
        try {
            log.info("Consultando estudiante {} en student-service", studentId);
            return restClient.get()
                    .uri("/v1/api/student/find/{id}", studentId)
                    .retrieve()
                    .body(StudentResponse.class);
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new ResourceNotFoundException("No existe un estudiante con ID: " + studentId);
            }
            throw new IllegalArgumentException("No se pudo consultar student-service: " + ex.getMessage());
        }
    }
}
