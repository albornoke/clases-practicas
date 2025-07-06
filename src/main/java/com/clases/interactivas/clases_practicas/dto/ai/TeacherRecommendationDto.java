package com.clases.interactivas.clases_practicas.dto.ai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRecommendationDto {

    private String suggestion;
    private Double confidenceScore; 
}