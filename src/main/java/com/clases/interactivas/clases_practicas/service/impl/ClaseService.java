package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.util.*;
import com.clases.interactivas.clases_practicas.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    public List<Clase> getAllClases() {
        return claseRepository.findAll();
    }

    public Clase getClaseById(Long id) {
        return claseRepository.findById(id).orElse(null);
    }

    public Clase createClase(Clase clase) {
       if(clase.getFecha() == null || clase.getHoraInicio() == null || clase.getHoraFin() == null || 
          clase.getDocente() == null || clase.getMateria() == null){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        if(clase.getHoraInicio().after(clase.getHoraFin())){
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }
        if(clase.getUrl() != null && !clase.getUrl().trim().isEmpty()){
            if(!clase.getUrl().startsWith("https://")){
                throw new IllegalArgumentException("La URL debe comenzar con https://");
            }
        }
        List<Clase> clasesExistentes = claseRepository.findByDocenteAndFechaAndHoraInicio(clase.getDocente(), clase.getFecha(), clase.getHoraInicio());
        for(Clase claseExistente : clasesExistentes){
            if(ValidationUtil.isTimeOverlap(claseExistente.getHoraInicio(), claseExistente.getHoraFin(), clase.getHoraInicio(), clase.getHoraFin())){
                throw new IllegalArgumentException("El docente ya tiene una clase en ese horario");
            }
        }
        return claseRepository.save(clase);
    }

    public Clase updateClase(Long id, Clase clase) {
     Clase existingClase = claseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Clase no encontrada" + id));

     if (clase.getFecha() == null || clase.getHoraInicio() == null ||  clase.getHoraFin() == null || clase.getTema() == null || 
     clase.getTema().trim().isEmpty() || clase.getDocente() == null) {
     throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos: fecha, horaInicio, horaFin, tema, docente");
     }

     if (!clase.getHoraFin().after(clase.getHoraInicio())) {
        throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
    }

    if (clase.getUrl() != null && !clase.getUrl().trim().isEmpty()) {
        if (!ValidationUtil.isValidUrl(clase.getUrl())) {
            throw new IllegalArgumentException("El enlace de reunión proporcionado no es una URL válida");
        }
    }
    // Validación de solapamiento de horarios (excluyendo la clase actual)
    List<Clase> clasesExistentes = claseRepository.findByDocenteAndFechaAndHoraInicio(clase.getDocente(), clase.getFecha(), clase.getHoraInicio());
    for (Clase existente : clasesExistentes) {
        if (existente.getId() != id &&
            ValidationUtil.isTimeOverlap(
                existente.getHoraInicio(), existente.getHoraFin(),
                clase.getHoraInicio(), clase.getHoraFin())) {
            throw new IllegalArgumentException("El docente ya tiene otra clase programada en ese horario");
        }
    }

    existingClase.setFecha(clase.getFecha());
    existingClase.setHoraInicio(clase.getHoraInicio());
    existingClase.setHoraFin(clase.getHoraFin());
    existingClase.setTema(clase.getTema());
    existingClase.setEnlace_reunion(clase.getUrl());
    existingClase.setDocente(clase.getDocente());

    return claseRepository.save(existingClase);
    }
   
    public void deleteClase(Long id) {
        claseRepository.deleteById(id);
    }

    public List<Clase> findByDocente(Docente docente) {
        return claseRepository.findByDocente(docente);
    }

    public List<Clase> findByFecha(Date fecha) {
        return claseRepository.findByFecha(fecha);
    }

    public List<Clase> findByHoraInicio(Time horaInicio) {
        return claseRepository.findByHoraInicio(horaInicio);
    }
}