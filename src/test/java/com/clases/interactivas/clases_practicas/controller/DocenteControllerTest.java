package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.dto.request.RegistroDocenteRequest;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.DocenteService;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService; // Importar UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DocenteController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false)
class DocenteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocenteService docenteService;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService userDetailsService;


    private Docente testDocente;
    private Usuario testUsuario;
    private RegistroDocenteRequest registroDocenteRequest;

    @BeforeEach
    void setUp() {
        testUsuario = new Usuario();
        testUsuario.setId(1L);
        testUsuario.setEmail("docente@example.com");
        testUsuario.setPassword("hashedpassword");
        testUsuario.setRol("DOCENTE");

        testDocente = new Docente();
        testDocente.setId(1L);
        testDocente.setNombre("Maria");
        testDocente.setApellido("Albornoz");
        testDocente.setTelefono("3127626430");
        testDocente.setDescripcion("Docente de Biologia");
        testDocente.setFotoUrl("http://fotodeprueba");
        testDocente.setUsuario(testUsuario); // Asociar el usuario al docente
        testDocente.setClases(Collections.emptyList()); // Inicializar clases

        registroDocenteRequest = new RegistroDocenteRequest();
        registroDocenteRequest.setNombre("Nuevo");
        registroDocenteRequest.setApellido("Docente");
        registroDocenteRequest.setTelefono("1234567890");
        registroDocenteRequest.setDescripcion("Docente de Prueba");
        registroDocenteRequest.setCorreo("nuevodocente@example.com");
        registroDocenteRequest.setContraseña("passwordsegura");
        registroDocenteRequest.setFoto(new MockMultipartFile("foto", "foto.jpg", "image/jpeg", "some image".getBytes()));
    }

    @Test
    void getAllDocentes() throws Exception {
        when(docenteService.getAllDocentes()).thenReturn(Collections.singletonList(testDocente));
        mockMvc.perform(get("/api/docente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(testDocente.getNombre()));
    }

    @Test
    void getDocenteById() throws Exception {
        when(docenteService.getDocenteById(1L)).thenReturn(testDocente);
        mockMvc.perform(get("/api/docente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testDocente.getId()));
    }

    @Test
    void getDocenteByIdNotFound() throws Exception {
        when(docenteService.getDocenteById(1L)).thenReturn(null);
        mockMvc.perform(get("/api/docente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDocente() throws Exception {
        when(docenteService.createDocente(any(Docente.class))).thenReturn(testDocente);
        mockMvc.perform(post("/api/docente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testDocente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testDocente.getId()));
    }

    @Test
    void updateDocente() throws Exception {
        Docente updatedDocente = new Docente();
        updatedDocente.setId(1L);
        updatedDocente.setNombre("Maria Updated");
        updatedDocente.setApellido("Albornoz Updated");
        updatedDocente.setTelefono("9876543210");
        updatedDocente.setDescripcion("Docente de Quimica");
        updatedDocente.setFotoUrl("http://fotoupdated");
        updatedDocente.setUsuario(testUsuario);
        updatedDocente.setClases(Collections.emptyList());

        when(docenteService.updateDocente(eq(1L), any(Docente.class))).thenReturn(updatedDocente);
        mockMvc.perform(put("/api/docente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedDocente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(updatedDocente.getNombre()));
    }

    @Test
    void deleteDocente() throws Exception {
        doNothing().when(docenteService).deleteDocente(1L);
        mockMvc.perform(delete("/api/docente/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void registrarDocente_Success() throws Exception {
        Docente expectedDocente = new Docente();
        expectedDocente.setId(2L);
        expectedDocente.setNombre(registroDocenteRequest.getNombre());
        expectedDocente.setApellido(registroDocenteRequest.getApellido());
        expectedDocente.setTelefono(registroDocenteRequest.getTelefono());
        expectedDocente.setDescripcion(registroDocenteRequest.getDescripcion());

        expectedDocente.setFotoUrl("http://uploaded_foto_url.jpg");
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setId(2L);
        expectedUsuario.setEmail(registroDocenteRequest.getCorreo());
        expectedUsuario.setPassword("hashedpassword_new");
        expectedUsuario.setRol("DOCENTE");
        expectedDocente.setUsuario(expectedUsuario);
        expectedDocente.setClases(Collections.emptyList());

        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(expectedUsuario);
        when(docenteService.createDocente(any(RegistroDocenteRequest.class))).thenReturn(expectedDocente);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/docente/registro")
                        .file((MockMultipartFile) registroDocenteRequest.getFoto())
                        .param("nombre", registroDocenteRequest.getNombre())
                        .param("apellido", registroDocenteRequest.getApellido())
                        .param("telefono", registroDocenteRequest.getTelefono())
                        .param("descripcion", registroDocenteRequest.getDescripcion())
                        .param("correo", registroDocenteRequest.getCorreo())
                        .param("contraseña", registroDocenteRequest.getContraseña())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(registroDocenteRequest.getNombre())) // Espera "Nuevo"
                .andExpect(jsonPath("$.apellido").value(registroDocenteRequest.getApellido()))
                .andExpect(jsonPath("$.usuario.email").value(registroDocenteRequest.getCorreo()));
    }

    @Test
    void registrarDocente_ValidationError() throws Exception {
        registroDocenteRequest.setNombre("");
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/docente/registro")
                .file((MockMultipartFile) registroDocenteRequest.getFoto())
                .param("nombre", registroDocenteRequest.getNombre())
                .param("apellido", registroDocenteRequest.getApellido())
                .param("telefono", registroDocenteRequest.getTelefono())
                .param("descripcion", registroDocenteRequest.getDescripcion())
                .param("correo", registroDocenteRequest.getCorreo())
                .param("contraseña", registroDocenteRequest.getContraseña())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.field=='nombre' && @.message=='El nombre no puede estar vacío')]").exists());
    }
    
    @Test
    void testUploadImage_invalidType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", // debe coincidir con @RequestParam
                "test.txt",
                "text/plain",
                "contenido de texto".getBytes());
        mockMvc.perform(multipart("/api/docente/upload-image/1")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Solo se permiten archivos de imagen"));
    }

    @Test
    void testUploadImage_tooLarge() throws Exception {
        byte[] largeContent = new byte[2 * 1024 * 1024]; // 2MB
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "large.jpg",
                "image/jpeg",
                largeContent);
        mockMvc.perform(multipart("/api/docente/upload-image/1")
                        .file(file))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("$.message").value("El archivo excede el tamaño máximo permitido (1MB)"));
    }

}