package com.clases.interactivas.clases_practicas.service.ia;

import com.clases.interactivas.clases_practicas.dto.ai.StudentBehaviorDto;
import com.clases.interactivas.clases_practicas.dto.ai.TeacherRecommendationDto;

public interface AiLearningService {

    /**
     * Envía el comportamiento del estudiante a la API de IA y obtiene recomendaciones.
     * @param studentBehavior Información sobre el comportamiento del estudiante.
     * @return Recomendaciones para el docente, o null si falla.
     */
    TeacherRecommendationDto obtenerRecomendacionParaDocente(StudentBehaviorDto studentBehavior);

}