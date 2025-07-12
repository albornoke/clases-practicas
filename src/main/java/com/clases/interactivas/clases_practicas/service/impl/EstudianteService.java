package com.clases.interactivas.clases_practicas.service.impl;

import com.clases.interactivas.clases_practicas.dto.request.RegistroEstudianteRequest;
import com.clases.interactivas.clases_practicas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clases.interactivas.clases_practicas.repository.EstudianteRepository;
import java.util.List;
import com.clases.interactivas.clases_practicas.model.Estudiante;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FileStorageService fileStorageService;

    public Estudiante createEstudiante(RegistroEstudianteRequest request){
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getCorreo());
        usuario.setPassword(request.getContraseña());
        usuario.setRol("ESTUDIANTE");
        Usuario usuarioCreado = usuarioService.registrarUsuario(usuario);
        String fotoUrl = null;
        if(request.getFoto() != null && !request.getFoto().isEmpty()){
            fotoUrl = fileStorageService.storeFile(request.getFoto());
        }
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.getNombre());
        estudiante.setApellido(request.getApellido());
        estudiante.setTelefono(request.getTelefono());
        estudiante.setDescripcion(request.getDescripcion());
        estudiante.setGrado(request.getGrado());
        estudiante.setFotoUrl(fotoUrl);
        estudiante.setUsuario(usuarioCreado);
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Estudiante getEstudianteById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public Estudiante createEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante updateEstudiante(Long id, Estudiante estudiante) {
        if(estudianteRepository.existsById(id)) {
            estudiante.setId(id);
            return estudianteRepository.save(estudiante);
        }
        return null;
    }

    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    public List<Estudiante> findByNombre(String nombre) {
        return estudianteRepository.findByNombre(nombre);
    }

    public List<Estudiante> findByApellido(String apellido) {
        return estudianteRepository.findByApellido(apellido);
    }
}
