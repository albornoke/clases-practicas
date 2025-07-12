package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Guia;
import com.clases.interactivas.clases_practicas.service.impl.GuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guia")
@CrossOrigin(origins = "http://localhost:5173")
public class GuiaController {

    @Autowired
    private GuiaService guiaService;

    @GetMapping
    public List<Guia> getAllGuias() {
        return guiaService.getAllGuias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guia> getGuiaById(@PathVariable Long id) {
        Guia guia = guiaService.getGuiaById(id);
        return guia != null ? ResponseEntity.ok(guia) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Guia createGuia(@RequestBody Guia guia) {
        return guiaService.createGuia(guia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guia> updateGuia(@PathVariable Long id, @RequestBody Guia guia) {
        Guia updatedGuia = guiaService.updateGuia(id, guia);
        return updatedGuia != null ? ResponseEntity.ok(updatedGuia) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuia(@PathVariable Long id) {
        guiaService.deleteGuia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulo/{titulo}")
    public List<Guia> findByTitulo(@PathVariable String titulo) {
        return guiaService.findByTitulo(titulo);
    }

    @GetMapping("/docente/{docenteId}")
    public List<Guia> findByDocenteId(@PathVariable Long docenteId) {
        return guiaService.findByDocenteId(docenteId);
    }

    @GetMapping("/fecha-publicacion/{fechaPublicacion}")
    public List<Guia> findByFechaPublicacion(@PathVariable LocalDate fechaPublicacion) {
        return guiaService.findByFechaPublicacion(fechaPublicacion);
    }

    @GetMapping("/descripcion/{descripcion}")
    public List<Guia> findByDescripcion(@PathVariable String descripcion) {
        return guiaService.findByDescripcion(descripcion);
    }
}