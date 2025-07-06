package com.clases.interactivas.clases_practicas.repository;

import com.clases.interactivas.clases_practicas.model.Contacto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByCorreo(String correo);
    List<Contacto> findByTelefono(String telefono);
}
