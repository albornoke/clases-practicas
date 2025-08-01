package com.clases.interactivas.clases_practicas.repository;

import com.clases.interactivas.clases_practicas.model.Examen;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    
    // Buscar por docente
    List<Examen> findByDocente(Docente docente);
    
    // Buscar por estudiante
    List<Examen> findByEstudiante(Estudiante estudiante);
    
    // Buscar por materia
    List<Examen> findByMateria(String materia);
    
    // Buscar por clase
    List<Examen> findByClase(Clase clase);
    
    // Buscar por docente y estudiante
    List<Examen> findByDocenteAndEstudiante(Docente docente, Estudiante estudiante);
    
    // Buscar por docente y materia
    List<Examen> findByDocenteAndMateria(Docente docente, String materia);
    
    // Buscar exámenes pendientes de calificar por docente
    @Query("SELECT e FROM Examen e WHERE e.docente = :docente AND e.calificacion IS NULL AND e.fechaRealizacion IS NOT NULL")
    List<Examen> findExamenesPendientesCalificar(@Param("docente") Docente docente);
    
    // Buscar exámenes no realizados por estudiante
    @Query("SELECT e FROM Examen e WHERE e.estudiante = :estudiante AND e.fechaRealizacion IS NULL")
    List<Examen> findExamenesNoRealizados(@Param("estudiante") Estudiante estudiante);
    
    // Buscar exámenes vencidos
    @Query("SELECT e FROM Examen e WHERE e.fechaLimite < :fechaActual AND e.fechaRealizacion IS NULL")
    List<Examen> findExamenesVencidos(@Param("fechaActual") LocalDateTime fechaActual);
    
    // Buscar exámenes por rango de fechas
    List<Examen> findByFechaLimiteBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar exámenes aprobados por estudiante
    @Query("SELECT e FROM Examen e WHERE e.estudiante = :estudiante AND e.aprobado = true")
    List<Examen> findExamenesAprobados(@Param("estudiante") Estudiante estudiante);
    
    // Buscar exámenes reprobados por estudiante
    @Query("SELECT e FROM Examen e WHERE e.estudiante = :estudiante AND e.aprobado = false")
    List<Examen> findExamenesReprobados(@Param("estudiante") Estudiante estudiante);
    
    // Contar exámenes pendientes por docente
    @Query("SELECT COUNT(e) FROM Examen e WHERE e.docente = :docente AND e.calificacion IS NULL AND e.fechaRealizacion IS NOT NULL")
    Long countExamenesPendientesPorDocente(@Param("docente") Docente docente);
    
    // Buscar por título (búsqueda parcial)
    List<Examen> findByTituloContainingIgnoreCase(String titulo);
}