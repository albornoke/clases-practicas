package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Examen;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.service.impl.ExamenService;
import com.clases.interactivas.clases_practicas.service.impl.DocenteService;
import com.clases.interactivas.clases_practicas.service.impl.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examen")
@CrossOrigin(origins = "http://localhost:5173")
public class ExamenController {
    
    @Autowired
    private ExamenService examenService;
    
    @Autowired
    private DocenteService docenteService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    // Operaciones CRUD básicas
    @GetMapping
    public ResponseEntity<List<Examen>> getAllExamenes() {
        List<Examen> examenes = examenService.getAllExamenes();
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable Long id) {
        Examen examen = examenService.getExamenById(id);
        return examen != null ? ResponseEntity.ok(examen) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> createExamen(@Valid @RequestBody Examen examen, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> Map.of(
                            "field", error.getField(),
                            "message", error.getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "message", "Error de validación",
                    "errors", errors
            ));
        }
        
        try {
            Examen nuevoExamen = examenService.createExamen(examen);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoExamen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExamen(@PathVariable Long id, @RequestBody Examen examen) {
        try {
            Examen examenActualizado = examenService.updateExamen(id, examen);
            return ResponseEntity.ok(examenActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExamen(@PathVariable Long id) {
        try {
            examenService.deleteExamen(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    // Endpoints de búsqueda
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Examen>> getExamenesByDocente(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesByDocente(docente);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Examen>> getExamenesByEstudiante(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesByEstudiante(estudiante);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/materia/{materia}")
    public ResponseEntity<List<Examen>> getExamenesByMateria(@PathVariable String materia) {
        List<Examen> examenes = examenService.getExamenesByMateria(materia);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/docente/{docenteId}/pendientes")
    public ResponseEntity<List<Examen>> getExamenesPendientesCalificar(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesPendientesCalificar(docente);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/estudiante/{estudianteId}/no-realizados")
    public ResponseEntity<List<Examen>> getExamenesNoRealizados(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesNoRealizados(estudiante);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/vencidos")
    public ResponseEntity<List<Examen>> getExamenesVencidos() {
        List<Examen> examenes = examenService.getExamenesVencidos();
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/estudiante/{estudianteId}/aprobados")
    public ResponseEntity<List<Examen>> getExamenesAprobados(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesAprobados(estudiante);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/estudiante/{estudianteId}/reprobados")
    public ResponseEntity<List<Examen>> getExamenesReprobados(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Examen> examenes = examenService.getExamenesReprobados(estudiante);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Examen>> buscarPorTitulo(@RequestParam String titulo) {
        List<Examen> examenes = examenService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(examenes);
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Examen>> getExamenesPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Examen> examenes = examenService.getExamenesPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(examenes);
    }
    
    // Endpoints de acciones
    @PutMapping("/{id}/realizar")
    public ResponseEntity<?> marcarComoRealizado(@PathVariable Long id) {
        try {
            Examen examen = examenService.marcarComoRealizado(id);
            return ResponseEntity.ok(examen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}/calificar")
    public ResponseEntity<?> calificarExamen(
            @PathVariable Long id,
            @RequestBody Map<String, Object> calificacionData) {
        try {
            Double calificacion = Double.valueOf(calificacionData.get("calificacion").toString());
            String observaciones = (String) calificacionData.get("observaciones");
            
            Examen examen = examenService.calificarExamen(id, calificacion, observaciones);
            return ResponseEntity.ok(examen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    // Endpoints de estadísticas
    @GetMapping("/docente/{docenteId}/count-pendientes")
    public ResponseEntity<Map<String, Long>> countExamenesPendientes(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        Long count = examenService.countExamenesPendientes(docente);
        return ResponseEntity.ok(Map.of("pendientes", count));
    }
}