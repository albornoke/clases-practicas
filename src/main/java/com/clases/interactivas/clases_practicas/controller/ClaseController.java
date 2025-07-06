package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.service.impl.ClaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/clase")
@CrossOrigin(origins = "http://localhost:5173") // Considera restringir los orígenes en producción
@Validated
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public List<Clase> getAllClases() {
        return claseService.getAllClases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> getClaseById(@PathVariable Long id) {
        Clase clase = claseService.getClaseById(id);
        return clase != null ? ResponseEntity.ok(clase) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createClase(@RequestBody Clase clase) {
        try {
            Clase nuevaClase = claseService.createClase(clase);
            return ResponseEntity.ok(nuevaClase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la clase");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClase(@PathVariable Long id, @RequestBody Clase clase) {
        try {
            Clase updatedClase = claseService.updateClase(id, clase);
            return ResponseEntity.ok(updatedClase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la clase");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        claseService.deleteClase(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content es apropiado para delete exitoso
    }

    // Endpoints de búsqueda adicionales basados en tu ClaseRepository
    @GetMapping("/docente") // Considera usar un ID o un objeto más específico si es necesario
    public List<Clase> findByDocente(@RequestBody Docente docente) { // Recibir el objeto Docente puede ser complejo vía GET. Considera POST o parámetros más simples.
        return claseService.findByDocente(docente);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Clase> findByFecha(@PathVariable Date fecha) {
        return claseService.findByFecha(fecha);
    }

    @GetMapping("/hora-inicio/{horaInicio}")
    public List<Clase> findByHoraInicio(@PathVariable Time horaInicio) {
        return claseService.findByHoraInicio(horaInicio);
    }
}