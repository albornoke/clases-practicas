package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Actividad;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.service.impl.ActividadService;
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
@RequestMapping("/api/actividad")
@CrossOrigin(origins = "http://localhost:5173")
public class ActividadController {
    
    @Autowired
    private ActividadService actividadService;
    
    @Autowired
    private DocenteService docenteService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    // Operaciones CRUD básicas
    @GetMapping
    public ResponseEntity<List<Actividad>> getAllActividades() {
        List<Actividad> actividades = actividadService.getAllActividades();
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Actividad> getActividadById(@PathVariable Long id) {
        Actividad actividad = actividadService.getActividadById(id);
        return actividad != null ? ResponseEntity.ok(actividad) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> createActividad(@Valid @RequestBody Actividad actividad, BindingResult bindingResult) {
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
            Actividad nuevaActividad = actividadService.createActividad(actividad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaActividad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        try {
            Actividad actividadActualizada = actividadService.updateActividad(id, actividad);
            return ResponseEntity.ok(actividadActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActividad(@PathVariable Long id) {
        try {
            actividadService.deleteActividad(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    // Endpoints de búsqueda
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Actividad>> getActividadesByDocente(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesByDocente(docente);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Actividad>> getActividadesByEstudiante(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesByEstudiante(estudiante);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Actividad>> getActividadesByTipo(@PathVariable Actividad.TipoActividad tipo) {
        List<Actividad> actividades = actividadService.getActividadesByTipo(tipo);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/materia/{materia}")
    public ResponseEntity<List<Actividad>> getActividadesByMateria(@PathVariable String materia) {
        List<Actividad> actividades = actividadService.getActividadesByMateria(materia);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/docente/{docenteId}/pendientes")
    public ResponseEntity<List<Actividad>> getActividadesPendientesCalificar(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesPendientesCalificar(docente);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/estudiante/{estudianteId}/no-entregadas")
    public ResponseEntity<List<Actividad>> getActividadesNoEntregadas(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesNoEntregadas(estudiante);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/vencidas")
    public ResponseEntity<List<Actividad>> getActividadesVencidas() {
        List<Actividad> actividades = actividadService.getActividadesVencidas();
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/entregadas-tarde")
    public ResponseEntity<List<Actividad>> getActividadesEntregadasTarde() {
        List<Actividad> actividades = actividadService.getActividadesEntregadasTarde();
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/estudiante/{estudianteId}/aprobadas")
    public ResponseEntity<List<Actividad>> getActividadesAprobadas(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesAprobadas(estudiante);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/estudiante/{estudianteId}/reprobadas")
    public ResponseEntity<List<Actividad>> getActividadesReprobadas(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        List<Actividad> actividades = actividadService.getActividadesReprobadas(estudiante);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Actividad>> buscarPorTitulo(@RequestParam String titulo) {
        List<Actividad> actividades = actividadService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(actividades);
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Actividad>> getActividadesPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Actividad> actividades = actividadService.getActividadesPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(actividades);
    }
    
    // Endpoints de acciones
    @PutMapping("/{id}/entregar")
    public ResponseEntity<?> entregarActividad(
            @PathVariable Long id,
            @RequestBody Map<String, String> entregaData) {
        try {
            String archivoUrl = entregaData.get("archivoUrl");
            String observaciones = entregaData.get("observaciones");
            
            Actividad actividad = actividadService.entregarActividad(id, archivoUrl, observaciones);
            return ResponseEntity.ok(actividad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}/calificar")
    public ResponseEntity<?> calificarActividad(
            @PathVariable Long id,
            @RequestBody Map<String, Object> calificacionData) {
        try {
            Double calificacion = Double.valueOf(calificacionData.get("calificacion").toString());
            String observaciones = (String) calificacionData.get("observaciones");
            
            Actividad actividad = actividadService.calificarActividad(id, calificacion, observaciones);
            return ResponseEntity.ok(actividad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
    
    // Endpoints de estadísticas
    @GetMapping("/docente/{docenteId}/count-pendientes")
    public ResponseEntity<Map<String, Long>> countActividadesPendientes(@PathVariable Long docenteId) {
        Docente docente = docenteService.getDocenteById(docenteId);
        if (docente == null) {
            return ResponseEntity.notFound().build();
        }
        Long count = actividadService.countActividadesPendientes(docente);
        return ResponseEntity.ok(Map.of("pendientes", count));
    }
    
    @GetMapping("/estudiante/{estudianteId}/count-no-entregadas")
    public ResponseEntity<Map<String, Long>> countActividadesNoEntregadas(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        Long count = actividadService.countActividadesNoEntregadas(estudiante);
        return ResponseEntity.ok(Map.of("noEntregadas", count));
    }
}