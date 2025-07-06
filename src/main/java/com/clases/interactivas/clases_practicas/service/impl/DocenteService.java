package com.clases.interactivas.clases_practicas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.repository.DocenteRepository;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    public Docente getDocenteById(Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    public Docente createDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    public Docente updateDocente(Long id, Docente docente) {
        if (docenteRepository.existsById(id)) {
            docente.setId(id);
            return docenteRepository.save(docente); 
        }
        return null;
    }

    public void deleteDocente(Long id) {
        docenteRepository.deleteById(id);
    }

    public List<Docente> findByNombre(String nombre) {
        return docenteRepository.findByNombre(nombre);
    }

    public List<Docente> findByApellido(String apellido) {
        return docenteRepository.findByApellido(apellido);
    }
}
