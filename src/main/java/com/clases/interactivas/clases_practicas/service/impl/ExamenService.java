package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Examen;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamenService {
    
    @Autowired
    private ExamenRepository examenRepository;
    
    // Operaciones CRUD básicas
    public List<Examen> getAllExamenes() {
        return examenRepository.findAll();
    }
    
    public Examen getExamenById(Long id) {
        return examenRepository.findById(id).orElse(null);
    }
    
    public Examen createExamen(Examen examen) {
        validarExamen(examen);
        return examenRepository.save(examen);
    }
    
    public Examen updateExamen(Long id, Examen examen) {
        Optional<Examen> examenExistente = examenRepository.findById(id);
        if (examenExistente.isPresent()) {
            Examen examenActualizado = examenExistente.get();
            
            // Solo actualizar campos permitidos
            if (examen.getTitulo() != null) {
                examenActualizado.setTitulo(examen.getTitulo());
            }
            if (examen.getDescripcion() != null) {
                examenActualizado.setDescripcion(examen.getDescripcion());
            }
            if (examen.getFechaLimite() != null) {
                validarFechaLimite(examen.getFechaLimite());
                examenActualizado.setFechaLimite(examen.getFechaLimite());
            }
            if (examen.getDuracionMinutos() != null) {
                examenActualizado.setDuracionMinutos(examen.getDuracionMinutos());
            }
            if (examen.getObservaciones() != null) {
                examenActualizado.setObservaciones(examen.getObservaciones());
            }
            
            return examenRepository.save(examenActualizado);
        }
        throw new IllegalArgumentException("Examen no encontrado con ID: " + id);
    }
    
    public void deleteExamen(Long id) {
        if (!examenRepository.existsById(id)) {
            throw new IllegalArgumentException("Examen no encontrado con ID: " + id);
        }
        examenRepository.deleteById(id);
    }
    
    // Métodos de búsqueda específicos
    public List<Examen> getExamenesByDocente(Docente docente) {
        return examenRepository.findByDocente(docente);
    }
    
    public List<Examen> getExamenesByEstudiante(Estudiante estudiante) {
        return examenRepository.findByEstudiante(estudiante);
    }
    
    public List<Examen> getExamenesByMateria(String materia) {
        return examenRepository.findByMateria(materia);
    }
    
    public List<Examen> getExamenesByClase(Clase clase) {
        return examenRepository.findByClase(clase);
    }
    
    public List<Examen> getExamenesByDocenteAndEstudiante(Docente docente, Estudiante estudiante) {
        return examenRepository.findByDocenteAndEstudiante(docente, estudiante);
    }
    
    public List<Examen> getExamenesPendientesCalificar(Docente docente) {
        return examenRepository.findExamenesPendientesCalificar(docente);
    }
    
    public List<Examen> getExamenesNoRealizados(Estudiante estudiante) {
        return examenRepository.findExamenesNoRealizados(estudiante);
    }
    
    public List<Examen> getExamenesVencidos() {
        return examenRepository.findExamenesVencidos(LocalDateTime.now());
    }
    
    public List<Examen> getExamenesAprobados(Estudiante estudiante) {
        return examenRepository.findExamenesAprobados(estudiante);
    }
    
    public List<Examen> getExamenesReprobados(Estudiante estudiante) {
        return examenRepository.findExamenesReprobados(estudiante);
    }
    
    public Long countExamenesPendientes(Docente docente) {
        return examenRepository.countExamenesPendientesPorDocente(docente);
    }
    
    public List<Examen> buscarPorTitulo(String titulo) {
        return examenRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    // Métodos de lógica de negocio
    public Examen marcarComoRealizado(Long examenId) {
        Examen examen = getExamenById(examenId);
        if (examen == null) {
            throw new IllegalArgumentException("Examen no encontrado");
        }
        
        if (examen.estaRealizado()) {
            throw new IllegalStateException("El examen ya fue realizado");
        }
        
        if (examen.estaVencido()) {
            throw new IllegalStateException("El examen está vencido");
        }
        
        examen.marcarComoRealizado();
        return examenRepository.save(examen);
    }
    
    public Examen calificarExamen(Long examenId, Double calificacion, String observaciones) {
        Examen examen = getExamenById(examenId);
        if (examen == null) {
            throw new IllegalArgumentException("Examen no encontrado");
        }
        
        if (!examen.estaRealizado()) {
            throw new IllegalStateException("No se puede calificar un examen que no ha sido realizado");
        }
        
        validarCalificacion(calificacion);
        examen.calificar(calificacion, observaciones);
        return examenRepository.save(examen);
    }
    
    public List<Examen> getExamenesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return examenRepository.findByFechaLimiteBetween(fechaInicio, fechaFin);
    }
    
    // Métodos de validación
    private void validarExamen(Examen examen) {
        if (examen.getTitulo() == null || examen.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del examen es obligatorio");
        }
        
        if (examen.getFechaLimite() == null) {
            throw new IllegalArgumentException("La fecha límite es obligatoria");
        }
        
        validarFechaLimite(examen.getFechaLimite());
        
        if (examen.getDocente() == null) {
            throw new IllegalArgumentException("El docente es obligatorio");
        }
        
        if (examen.getEstudiante() == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        
        if (examen.getMateria() == null || examen.getMateria().trim().isEmpty()) {
            throw new IllegalArgumentException("La materia es obligatoria");
        }
        
        if (examen.getDuracionMinutos() != null && examen.getDuracionMinutos() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos");
        }
    }
    
    private void validarFechaLimite(LocalDateTime fechaLimite) {
        if (fechaLimite.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha límite no puede ser en el pasado");
        }
    }
    
    private void validarCalificacion(Double calificacion) {
        if (calificacion == null) {
            throw new IllegalArgumentException("La calificación es obligatoria");
        }
        
        if (calificacion < 0.0 || calificacion > 5.0) {
            throw new IllegalArgumentException("La calificación debe estar entre 0.0 y 5.0");
        }
    }
}