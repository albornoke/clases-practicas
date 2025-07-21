package com.clases.interactivas.clases_practicas.service.impl;

import java.util.List;
import com.clases.interactivas.clases_practicas.dto.request.RegistroDocenteRequest;
import com.clases.interactivas.clases_practicas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.repository.DocenteRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FileStorageService fileStorageService; //Servicio para manejar archivos

    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    public Docente getDocenteById(Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    public Docente createDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    public Docente createDocente(RegistroDocenteRequest request) {
       //Crear el usuario primero
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getCorreo());
        usuario.setPassword(request.getContraseña());
        usuario.setRol("DOCENTE");
        Usuario usuarioCreado = usuarioService.registrarUsuario(usuario);
      //Manejo de foto
        String fotoUrl = null;
        if(request.getFoto() != null && !request.getFoto().isEmpty()){
            fotoUrl = fileStorageService.storeFile(request.getFoto());
        }
      //Crear el docente
        Docente docente = new Docente();
        docente.setNombre(request.getNombre());
        docente.setApellido(request.getApellido());
        docente.setTelefono(request.getTelefono());
        docente.setDescripcion(request.getDescripcion());
        docente.setFotoUrl(fotoUrl);
        docente.setUsuario(usuarioCreado);

        return docenteRepository.save(docente);
    }

    public Docente updateDocente(Long id, Docente docente) {
        if (docenteRepository.existsById(id)) {
            docente.setId(id);
            return docenteRepository.save(docente); 
        }
        return null;
    }

    public void deleteDocente(Long id) {
        docenteRepository.deleteById(id);
    }

    public List<Docente> findByNombre(String nombre) {
        return docenteRepository.findByNombre(nombre);
    }

    public List<Docente> findByApellido(String apellido) {
        return docenteRepository.findByApellido(apellido);
    }

    public String uploadImage(Long id, MultipartFile file) {
        Docente docente = getDocenteById(id);
        if (docente == null) {
            throw new IllegalArgumentException("Docente no encontrado");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Archivo vacío");
        }
        // Validar tipo de archivo
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/webp") || contentType.equals("image/avif"))) {
            throw new IllegalArgumentException("Tipo de archivo no soportado");
        }
        // Validar tamaño (máx 2MB)
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("El archivo excede el tamaño máximo de 2MB");
        }
        String fotoUrl = fileStorageService.storeFile(file);
        docente.setFotoUrl(fotoUrl);
        docenteRepository.save(docente);
        return contentType;
    }
}
