package com.clases.interactivas.clases_practicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clases.interactivas.clases_practicas.repository.EstudianteRepository;
import java.util.List;
import com.clases.interactivas.clases_practicas.model.Estudiante;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Estudiante getEstudianteById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public Estudiante createEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante updateEstudiante(Long id, Estudiante estudiante) {
        if(estudianteRepository.existsById(id)) {
            estudiante.setId(id);
            estudianteRepository.save(estudiante);
        }
        return null;
    }

    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    public List<Estudiante> findByNombre(String nombre) {
        return estudianteRepository.findByNombre(nombre);
    }

    public List<Estudiante> findByApellido(String apellido) {
        return estudianteRepository.findByApellido(apellido);
    }
}
