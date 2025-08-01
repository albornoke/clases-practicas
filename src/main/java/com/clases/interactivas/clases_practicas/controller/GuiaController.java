package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Guia;
import com.clases.interactivas.clases_practicas.service.impl.GuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createGuia(
            @RequestPart("guia") Guia guia,
            @RequestPart("file") MultipartFile file) {
        // Validar tipo de archivo
        String contentType = file.getContentType();
        if (!(contentType != null && (contentType.equals("application/pdf") ||
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")))) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Solo se permiten archivos PDF y Word"
            ));
        }
        // Validar tamaño (5MB máximo)
        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(Map.of(
                    "message", "El archivo excede el tamaño máximo permitido (5MB)"
            ));
        }
        return ResponseEntity.ok(guiaService.createGuia(guia, file));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateGuia(
            @PathVariable Long id,
            @RequestPart("guia") Guia guia,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        
        if (file != null) {
            String contentType = file.getContentType();
            if (!(contentType != null && (contentType.equals("application/pdf") ||
                    contentType.equals("application/msword") ||
                    contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")))) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "Solo se permiten archivos PDF y Word"
                ));
            }
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(Map.of(
                        "message", "El archivo excede el tamaño máximo permitido (5MB)"
                ));
            }
        }

        Guia updatedGuia = guiaService.updateGuia(id, guia, file);
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