package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Actividad;
import com.clases.interactivas.clases_practicas.model.Actividad.TipoActividad;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActividadService {
    
    @Autowired
    private ActividadRepository actividadRepository;
    
    // Operaciones CRUD básicas
    public List<Actividad> getAllActividades() {
        return actividadRepository.findAll();
    }
    
    public Actividad getActividadById(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }
    
    public Actividad createActividad(Actividad actividad) {
        validarActividad(actividad);
        return actividadRepository.save(actividad);
    }
    
    public Actividad updateActividad(Long id, Actividad actividad) {
        Optional<Actividad> actividadExistente = actividadRepository.findById(id);
        if (actividadExistente.isPresent()) {
            Actividad actividadActualizada = actividadExistente.get();
            
            // Solo actualizar campos permitidos
            if (actividad.getTitulo() != null) {
                actividadActualizada.setTitulo(actividad.getTitulo());
            }
            if (actividad.getDescripcion() != null) {
                actividadActualizada.setDescripcion(actividad.getDescripcion());
            }
            if (actividad.getFechaLimite() != null) {
                validarFechaLimite(actividad.getFechaLimite());
                actividadActualizada.setFechaLimite(actividad.getFechaLimite());
            }
            if (actividad.getTipo() != null) {
                actividadActualizada.setTipo(actividad.getTipo());
            }
            if (actividad.getObservaciones() != null) {
                actividadActualizada.setObservaciones(actividad.getObservaciones());
            }
            
            return actividadRepository.save(actividadActualizada);
        }
        throw new IllegalArgumentException("Actividad no encontrada con ID: " + id);
    }
    
    public void deleteActividad(Long id) {
        if (!actividadRepository.existsById(id)) {
            throw new IllegalArgumentException("Actividad no encontrada con ID: " + id);
        }
        actividadRepository.deleteById(id);
    }
    
    // Métodos de búsqueda específicos
    public List<Actividad> getActividadesByDocente(Docente docente) {
        return actividadRepository.findByDocente(docente);
    }
    
    public List<Actividad> getActividadesByEstudiante(Estudiante estudiante) {
        return actividadRepository.findByEstudiante(estudiante);
    }
    
    public List<Actividad> getActividadesByTipo(TipoActividad tipo) {
        return actividadRepository.findByTipo(tipo);
    }
    
    public List<Actividad> getActividadesByMateria(String materia) {
        return actividadRepository.findByMateria(materia);
    }
    
    public List<Actividad> getActividadesByClase(Clase clase) {
        return actividadRepository.findByClase(clase);
    }
    
    public List<Actividad> getActividadesByDocenteAndEstudiante(Docente docente, Estudiante estudiante) {
        return actividadRepository.findByDocenteAndEstudiante(docente, estudiante);
    }
    
    public List<Actividad> getActividadesByDocenteAndTipo(Docente docente, TipoActividad tipo) {
        return actividadRepository.findByDocenteAndTipo(docente, tipo);
    }
    
    public List<Actividad> getActividadesPendientesCalificar(Docente docente) {
        return actividadRepository.findActividadesPendientesCalificar(docente);
    }
    
    public List<Actividad> getActividadesNoEntregadas(Estudiante estudiante) {
        return actividadRepository.findActividadesNoEntregadas(estudiante);
    }
    
    public List<Actividad> getActividadesVencidas() {
        return actividadRepository.findActividadesVencidas(LocalDateTime.now());
    }
    
    public List<Actividad> getActividadesEntregadasTarde() {
        return actividadRepository.findActividadesEntregadasTarde();
    }
    
    public List<Actividad> getActividadesAprobadas(Estudiante estudiante) {
        return actividadRepository.findActividadesAprobadas(estudiante);
    }
    
    public List<Actividad> getActividadesReprobadas(Estudiante estudiante) {
        return actividadRepository.findActividadesReprobadas(estudiante);
    }
    
    public Long countActividadesPendientes(Docente docente) {
        return actividadRepository.countActividadesPendientesPorDocente(docente);
    }
    
    public List<Actividad> buscarPorTitulo(String titulo) {
        return actividadRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Actividad> getActividadesProximasVencer() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(1); // Próximas 24 horas
        return actividadRepository.findActividadesProximasVencer(ahora, limite);
    }
    
    // Métodos de lógica de negocio
    public Actividad entregarActividad(Long actividadId, String archivoUrl, String observaciones) {
        Actividad actividad = getActividadById(actividadId);
        if (actividad == null) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }
        
        if (actividad.estaEntregada()) {
            throw new IllegalStateException("La actividad ya fue entregada");
        }
        
        actividad.marcarComoEntregada(archivoUrl);
        if (observaciones != null && !observaciones.trim().isEmpty()) {
            actividad.setObservaciones(observaciones);
        }
        return actividadRepository.save(actividad);
    }
    
    public Actividad calificarActividad(Long actividadId, Double calificacion, String observaciones) {
        Actividad actividad = getActividadById(actividadId);
        if (actividad == null) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }
        
        if (!actividad.estaEntregada()) {
            throw new IllegalStateException("No se puede calificar una actividad que no ha sido entregada");
        }
        
        validarCalificacion(calificacion);
        actividad.calificar(calificacion, observaciones);
        return actividadRepository.save(actividad);
    }
    
    public List<Actividad> getActividadesPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return actividadRepository.findByFechaLimiteBetween(fechaInicio, fechaFin);
    }
    
    public List<Actividad> getActividadesByEstudianteAndTipo(Estudiante estudiante, TipoActividad tipo) {
        return actividadRepository.findByEstudianteAndTipo(estudiante, tipo);
    }
    
    // Métodos de estadísticas
    public long contarActividadesPorTipo(TipoActividad tipo) {
        return actividadRepository.findByTipo(tipo).size();
    }
    
    public long contarActividadesEntregadasTarde(Estudiante estudiante) {
        return actividadRepository.findByEstudiante(estudiante).stream()
                .mapToLong(a -> a.entregadaTarde() ? 1 : 0)
                .sum();
    }
    
    public Long countActividadesNoEntregadas(Estudiante estudiante) {
        return actividadRepository.findActividadesNoEntregadas(estudiante).stream().count();
    }
    
    // Métodos de validación
    private void validarActividad(Actividad actividad) {
        if (actividad.getTitulo() == null || actividad.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la actividad es obligatorio");
        }
        
        if (actividad.getTipo() == null) {
            throw new IllegalArgumentException("El tipo de actividad es obligatorio");
        }
        
        if (actividad.getFechaLimite() == null) {
            throw new IllegalArgumentException("La fecha límite es obligatoria");
        }
        
        validarFechaLimite(actividad.getFechaLimite());
        
        if (actividad.getDocente() == null) {
            throw new IllegalArgumentException("El docente es obligatorio");
        }
        
        if (actividad.getEstudiante() == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        
        if (actividad.getMateria() == null || actividad.getMateria().trim().isEmpty()) {
            throw new IllegalArgumentException("La materia es obligatoria");
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