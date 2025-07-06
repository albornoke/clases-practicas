package com.clases.interactivas.clases_practicas.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.model.Docente;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long>{
    List<Clase> findByDocente(Docente docente);
    List<Clase> findByFecha(Date fecha);
    List<Clase> findByHoraInicio(Time horaInicio);
    List<Clase> findByHoraFin(Time horaFin);
    List<Clase> findByMateria(String materia);
    List<Clase> findByDocenteAndFechaAndHoraInicio(Docente docente, Date fecha, Time horaInicio);
}
