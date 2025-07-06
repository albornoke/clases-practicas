package com.clases.interactivas.clases_practicas.repository;

import com.clases.interactivas.clases_practicas.model.Docente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> { 
    List<Docente> findByNombre(String nombre);
    List<Docente> findByApellido(String apellido);
    List<Docente> findByDescripcion(String descripcion);
    List<Docente> findByTelefono(String telefono);
}
