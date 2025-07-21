package com.clases.interactivas.clases_practicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clases.interactivas.clases_practicas.dto.request.RegistroEstudianteRequest;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.service.impl.EstudianteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/estudiante")
@CrossOrigin(origins = "http://localhost:5173")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.getAllEstudiantes();
    }

    @GetMapping("/online")
    public List<Estudiante> getEstudiantesOnline() {
        return estudianteService.getAllEstudiantes().stream()
            .filter(e -> e.getEstado() != null && e.getEstado().name().equals("ONLINE"))
            .toList();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.getEstudianteById(id);
        return estudiante != null ? ResponseEntity.ok(estudiante) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Estudiante createEstudiante(@Valid @RequestBody Estudiante estudiante) {
        return estudianteService.createEstudiante(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {        
        Estudiante updateEstudiante = estudianteService.updateEstudiante(id, estudiante);
        return updateEstudiante!= null? ResponseEntity.ok(updateEstudiante) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstudiante(@PathVariable Long id) {
        estudianteService.deleteEstudiante(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("seach/nombre/{nombre}")
    public List<Estudiante> findByNombre(@PathVariable String nombre){
        return estudianteService.findByNombre(nombre);
    }

    @GetMapping("seach/apellido/{apellido}")
    public List<Estudiante> findByApellido(@PathVariable String apellido){
        return estudianteService.findByApellido(apellido);
    }

    @PostMapping(value = "/registro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarEstudiante(
            @Valid @ModelAttribute RegistroEstudianteRequest registroEstudianteRequest){
        try{
            Estudiante estudiante = estudianteService.createEstudiante(registroEstudianteRequest);
            return ResponseEntity.ok(estudiante);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
