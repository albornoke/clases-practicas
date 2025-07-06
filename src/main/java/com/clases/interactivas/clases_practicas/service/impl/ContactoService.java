package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Contacto;
import com.clases.interactivas.clases_practicas.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    public List<Contacto> getAllContactos() {
        return contactoRepository.findAll();
    }

    public Contacto getContactoById(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    public Contacto createContacto(Contacto contacto) {
        // Aquí podrías agregar lógica de validación o negocio antes de guardar
        return contactoRepository.save(contacto);
    }

    public Contacto updateContacto(Long id, Contacto contacto) {
        if (contactoRepository.existsById(id)) {
            contacto.setId(id);
            // Aquí podrías agregar lógica de validación o negocio antes de actualizar
            return contactoRepository.save(contacto);
        }
        return null; // O lanzar una excepción si el contacto no se encuentra
    }

    public void deleteContacto(Long id) {
        contactoRepository.deleteById(id);
    }

    public List<Contacto> findByCorreo(String correo) {
        return contactoRepository.findByCorreo(correo);
    }

    public List<Contacto> findByTelefono(String telefono) {
        return contactoRepository.findByTelefono(telefono);
    }
}