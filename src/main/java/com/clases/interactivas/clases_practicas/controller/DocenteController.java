package com.clases.interactivas.clases_practicas.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.clases.interactivas.clases_practicas.dto.request.RegistroDocenteRequest;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.service.impl.DocenteService;

@RestController
@RequestMapping("/api/docente")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<Docente> getAllDocentes() {
        return docenteService.getAllDocentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> getDocenteById(@PathVariable Long id) {
        Docente docente = docenteService.getDocenteById(id);
        return docente != null ? ResponseEntity.ok(docente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Docente createDocente(@RequestBody Docente docente) {
        return docenteService.createDocente(docente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> updateDocente(@PathVariable Long id, @RequestBody Docente docente) {
        Docente updatedDocente = docenteService.updateDocente(id, docente);
        return updatedDocente != null ? ResponseEntity.ok(updatedDocente) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Long id) {
        docenteService.deleteDocente(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/nombre/{nombre}")
    public List<Docente> findByNombre(@PathVariable String nombre) {
        return docenteService.findByNombre(nombre);
    }

    @GetMapping("/search/apellido/{apellido}")
    public List<Docente> findByApellido(@PathVariable String apellido) {
        return docenteService.findByApellido(apellido);
    }

    @PostMapping(value = "/registro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarDocente(
            @Valid @ModelAttribute RegistroDocenteRequest registroDocenteRequest) {
        try {
            Docente docente = docenteService.createDocente(registroDocenteRequest);
            return ResponseEntity.ok(docente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
