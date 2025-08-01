package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Guia;
import com.clases.interactivas.clases_practicas.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class GuiaService {

    @Autowired
    private GuiaRepository guiaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<Guia> getAllGuias() {
        return guiaRepository.findAll();
    }

    public Guia getGuiaById(Long id) {
        return guiaRepository.findById(id).orElse(null);
    }

    public Guia createGuia(Guia guia, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        guia.setUrl(fileName);
        return guiaRepository.save(guia);
    }

    public Guia updateGuia(Long id, Guia guia, MultipartFile file) {
        return guiaRepository.findById(id)
                .map(existingGuia -> {
                    existingGuia.setTitulo(guia.getTitulo());
                    existingGuia.setDescripcion(guia.getDescripcion());
                    if (file != null) {
                        String fileName = fileStorageService.storeFile(file);
                        existingGuia.setUrl(fileName);
                    }
                    existingGuia.setFechaPublicacion(guia.getFechaPublicacion());
                    existingGuia.setDocente(guia.getDocente());
                    return guiaRepository.save(existingGuia);
                })
                .orElse(null);
    }

    public void deleteGuia(Long id) {
        guiaRepository.deleteById(id);
    }

    public List<Guia> findByTitulo(String titulo) {
        return guiaRepository.findByTitulo(titulo);
    }

    public List<Guia> findByDocenteId(Long docenteId) {
        return guiaRepository.findByDocenteId(docenteId);
    }

    public List<Guia> findByFechaPublicacion(LocalDate fechaPublicacion) {
        return guiaRepository.findByFechaPublicacion(fechaPublicacion);
    }

    public List<Guia> findByDescripcion(String descripcion) {
        return guiaRepository.findByDescripcion(descripcion);
    }
}