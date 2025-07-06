package com.clases.interactivas.clases_practicas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clases.interactivas.clases_practicas.model.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
    List<Estudiante> findByNombre(String nombre);
    List<Estudiante> findByApellido(String apellido);
    List<Estudiante> findByDescripcion(String descripcion);
    List<Estudiante> findByTelefono(String telefono);
}
