package com.clases.interactivas.clases_practicas.service.ia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity; // Asegúrate de que esta importación exista
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity; // Asegúrate de que esta importación exista
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // O la librería HTTP que prefieras

import com.clases.interactivas.clases_practicas.dto.ai.StudentBehaviorDto;
import com.clases.interactivas.clases_practicas.dto.ai.TeacherRecommendationDto;

// @Service
public class AiLearningServiceImpl implements AiLearningService {

    @Value("${ai.api.endpoint}")
    private String aiApiEndpoint;

    @Value("${ai.api.key}")
    private String aiApiKey;

    private final RestTemplate restTemplate;

    public AiLearningServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TeacherRecommendationDto obtenerRecomendacionParaDocente(StudentBehaviorDto studentBehavior) {
        // Lógica para construir la petición a la API de IA
        // (e.g., usando RestTemplate o el cliente HTTP que hayas elegido)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + aiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentBehaviorDto> entity = new HttpEntity<>(studentBehavior, headers);
        ResponseEntity<TeacherRecommendationDto> response = restTemplate.postForEntity(aiApiEndpoint, entity, TeacherRecommendationDto.class);
        return response.getBody();

        // System.out.println("Obteniendo recomendación para el comportamiento: " + studentBehavior.toString());
        // // Implementación pendiente - esto es solo un placeholder
        // TeacherRecommendationDto recommendation = new TeacherRecommendationDto();
        // recommendation.setSuggestion("Recomendación de ejemplo basada en el comportamiento del estudiante.");
        // return recommendation; 
    }
}