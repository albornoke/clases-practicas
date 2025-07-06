package com.clases.interactivas.clases_practicas.repository;

import com.clases.interactivas.clases_practicas.model.Guia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {
    List<Guia> findByTitulo(String titulo);
    List<Guia> findByDocenteId(Long docenteId); 
    List<Guia> findByFechaPublicacion(LocalDate fechaPublicacion);
    List<Guia> findByDescripcion(String descripcion);
}
