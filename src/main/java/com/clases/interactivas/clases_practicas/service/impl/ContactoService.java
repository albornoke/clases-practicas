package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Contacto;
import com.clases.interactivas.clases_practicas.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
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
        if(contacto.getFecha() == null || contacto.getFecha().isEmpty()){
            contacto.setFecha(java.time.LocalDate.now().toString());
        }
        if(contacto.getHora() == null || contacto.getHora().isEmpty()){
            contacto.setHora(java.time.LocalTime.now().toString());
        }
        return contactoRepository.save(contacto); }

    public Contacto updateContacto(Long id, Contacto contacto) {
        if (contactoRepository.existsById(id)) {
            contacto.setId(id);
            return contactoRepository.save(contacto);
        }
        return null;
    }

    public void deleteContacto(Long id) {
        contactoRepository.deleteById(id);
    }

    public List<Contacto> findByCorreo(String correo) { return contactoRepository.findByCorreo(correo); }

    public List<Contacto> findByTelefono(String telefono) { return contactoRepository.findByTelefono(telefono); }

    public List<Contacto> findByAsunto(String asunto) {return contactoRepository.findByAsunto(asunto); }

    public List<Contacto> findByMensaje(String mensaje) {return contactoRepository.findByMensaje(mensaje); }
}