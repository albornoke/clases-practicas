package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Contacto;
import com.clases.interactivas.clases_practicas.service.impl.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "http://localhost:5173") // Considera restringir los orígenes en producción
@Validated
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public List<Contacto> getAllContactos() {
        return contactoService.getAllContactos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactoById(@PathVariable Long id) {
        Contacto contacto = contactoService.getContactoById(id);
        return contacto != null ? ResponseEntity.ok(contacto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Contacto createContacto(@RequestBody Contacto contacto) {
        return contactoService.createContacto(contacto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> updateContacto(@PathVariable Long id, @RequestBody Contacto contacto) {
        Contacto updatedContacto = contactoService.updateContacto(id, contacto);
        return updatedContacto != null ? ResponseEntity.ok(updatedContacto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable Long id) {
        contactoService.deleteContacto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correo/{correo}")
    public List<Contacto> findByCorreo(@PathVariable String correo) {
        return contactoService.findByCorreo(correo);
    }

    @GetMapping("/telefono/{telefono}")
    public List<Contacto> findByTelefono(@PathVariable String telefono) {
        return contactoService.findByTelefono(telefono);
    }
}