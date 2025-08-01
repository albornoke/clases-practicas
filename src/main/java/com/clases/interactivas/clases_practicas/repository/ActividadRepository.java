package com.clases.interactivas.clases_practicas.repository;

import com.clases.interactivas.clases_practicas.model.Actividad;
import com.clases.interactivas.clases_practicas.model.Actividad.TipoActividad;
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
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    
    // Buscar por docente
    List<Actividad> findByDocente(Docente docente);
    
    // Buscar por estudiante
    List<Actividad> findByEstudiante(Estudiante estudiante);
    
    // Buscar por tipo de actividad
    List<Actividad> findByTipo(TipoActividad tipo);
    
    // Buscar por materia
    List<Actividad> findByMateria(String materia);
    
    // Buscar por clase
    List<Actividad> findByClase(Clase clase);
    
    // Buscar por docente y estudiante
    List<Actividad> findByDocenteAndEstudiante(Docente docente, Estudiante estudiante);
    
    // Buscar por docente y materia
    List<Actividad> findByDocenteAndMateria(Docente docente, String materia);
    
    // Buscar por docente y tipo
    List<Actividad> findByDocenteAndTipo(Docente docente, TipoActividad tipo);
    
    // Buscar actividades pendientes de calificar por docente
    @Query("SELECT a FROM Actividad a WHERE a.docente = :docente AND a.calificacion IS NULL AND a.fechaEntrega IS NOT NULL")
    List<Actividad> findActividadesPendientesCalificar(@Param("docente") Docente docente);
    
    // Buscar actividades no entregadas por estudiante
    @Query("SELECT a FROM Actividad a WHERE a.estudiante = :estudiante AND a.fechaEntrega IS NULL")
    List<Actividad> findActividadesNoEntregadas(@Param("estudiante") Estudiante estudiante);
    
    // Buscar actividades vencidas
    @Query("SELECT a FROM Actividad a WHERE a.fechaLimite < :fechaActual AND a.fechaEntrega IS NULL")
    List<Actividad> findActividadesVencidas(@Param("fechaActual") LocalDateTime fechaActual);
    
    // Buscar actividades entregadas tarde
    @Query("SELECT a FROM Actividad a WHERE a.fechaEntrega > a.fechaLimite")
    List<Actividad> findActividadesEntregadasTarde();
    
    // Buscar actividades por rango de fechas límite
    List<Actividad> findByFechaLimiteBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar actividades aprobadas por estudiante
    @Query("SELECT a FROM Actividad a WHERE a.estudiante = :estudiante AND a.aprobado = true")
    List<Actividad> findActividadesAprobadas(@Param("estudiante") Estudiante estudiante);
    
    // Buscar actividades reprobadas por estudiante
    @Query("SELECT a FROM Actividad a WHERE a.estudiante = :estudiante AND a.aprobado = false")
    List<Actividad> findActividadesReprobadas(@Param("estudiante") Estudiante estudiante);
    
    // Contar actividades pendientes por docente
    @Query("SELECT COUNT(a) FROM Actividad a WHERE a.docente = :docente AND a.calificacion IS NULL AND a.fechaEntrega IS NOT NULL")
    Long countActividadesPendientesPorDocente(@Param("docente") Docente docente);
    
    // Buscar por título (búsqueda parcial)
    List<Actividad> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar actividades por estudiante y tipo
    List<Actividad> findByEstudianteAndTipo(Estudiante estudiante, TipoActividad tipo);
    
    // Buscar actividades próximas a vencer (en las próximas 24 horas)
    @Query("SELECT a FROM Actividad a WHERE a.fechaLimite BETWEEN :ahora AND :limite AND a.fechaEntrega IS NULL")
    List<Actividad> findActividadesProximasVencer(@Param("ahora") LocalDateTime ahora, @Param("limite") LocalDateTime limite);
}