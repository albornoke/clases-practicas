package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; 

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));        
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("ESTUDIANTE"); // Rol por defecto
        } else {
            usuario.setRol(usuario.getRol().toUpperCase()); 
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                boolean passwordMatches = passwordEncoder.matches(password, usuario.getPassword());
                //System.out.println("Password matches: " + passwordMatches); // Agrega esta línea
                if (!passwordMatches) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
                }

        return usuario;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setEmail(usuario.getEmail());
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        if (usuario.getRol() != null && !usuario.getRol().isEmpty()) {
            usuarioExistente.setRol(usuario.getRol().toUpperCase()); // Normalizar a mayúsculas
        }
        usuarioExistente.setDocente(usuario.getDocente());
        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerPorRol(String rol) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getRol().equals(rol.toUpperCase()))
                .collect(Collectors.toList());
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
}
