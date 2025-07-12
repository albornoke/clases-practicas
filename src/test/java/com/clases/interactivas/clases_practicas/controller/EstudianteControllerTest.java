package com.clases.interactivas.clases_practicas.controller;
import com.clases.interactivas.clases_practicas.dto.request.RegistroEstudianteRequest;
import com.clases.interactivas.clases_practicas.enums.StatusEnum;
import com.clases.interactivas.clases_practicas.model.Estudiante;
import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.EstudianteService;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = EstudianteController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false)
public class EstudianteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EstudianteService estudianteService;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Estudiante testEstudiante;
    private Usuario testUsuario;
    private RegistroEstudianteRequest registroEstudianteRequest;

    @BeforeEach
    void setUp(){
        testUsuario = new Usuario();
        testUsuario.setId(1L);
        testUsuario.setEmail("estudiante@prueba.com");
        testUsuario.setPassword("123456789");
        testUsuario.setRol("ESTUDIANTE");

        testEstudiante = new Estudiante();
        testEstudiante.setId(1L);
        testEstudiante.setNombre("Scartt");
        testEstudiante.setApellido("Mina");
        testEstudiante.setTelefono("147852369");
        testEstudiante.setDescripcion("Estudiante de bachillerato");
        testEstudiante.setGrado("8");
        testEstudiante.setFotoUrl("http://fotoestudiante");
        testEstudiante.setUsuario(testUsuario);

        registroEstudianteRequest = new RegistroEstudianteRequest();
        registroEstudianteRequest.setNombre("nuevo");
        registroEstudianteRequest.setApellido("Estudiante");
        registroEstudianteRequest.setTelefono("123654789");
        registroEstudianteRequest.setDescripcion("Estdiante de prueba");
        registroEstudianteRequest.setGrado("8");
        registroEstudianteRequest.setCorreo("pruebaestudiante@correo.com");
        registroEstudianteRequest.setContraseña("password123");
        registroEstudianteRequest.setFoto(new MockMultipartFile("foto", "foto.jpg", "image/jpe", "some image".getBytes()));
    }

    @Test
    void testCreateEstudiante() throws Exception{
        when(estudianteService.createEstudiante(any(Estudiante.class))).thenReturn(testEstudiante);
        mockMvc.perform(post("/api/estudiante")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testEstudiante)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEstudiante.getId()));
    }

    @Test   
    void testDeleteEstudiante() throws Exception{
        doNothing().when(estudianteService).deleteEstudiante(1L);
        mockMvc.perform(delete("/api/estudiante/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByApellido() throws Exception {
        when(estudianteService.findByApellido("Mina")).thenReturn(Arrays.asList(testEstudiante));
        mockMvc.perform(get("/api/estudiante/seach/apellido/{apellido}", "Mina")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apellido").value(testEstudiante.getApellido()));
    }

    @Test
    void testFindByNombre() throws Exception{
        when(estudianteService.findByNombre("Scartt")).thenReturn(Arrays.asList(testEstudiante));
        mockMvc.perform(get("/api/estudiante/seach/nombre/{nombre}", "Scartt")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(testEstudiante.getNombre()));
    }

    @Test
    void testGetAllEstudiantes() throws Exception{
        when(estudianteService.getAllEstudiantes()).thenReturn(Arrays.asList(testEstudiante));
        mockMvc.perform(get("/api/estudiante")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(testEstudiante.getNombre()));

    }

    @Test
    void testGetEstudianteById() throws Exception{
        when(estudianteService.getEstudianteById(1L)).thenReturn(testEstudiante);
        mockMvc.perform(get("/api/estudiante/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEstudiante.getId()));
    }

    @Test
    void testGetEstudiantesOnline() throws Exception {
        Estudiante online = new Estudiante();
        online.setEstado(StatusEnum.ONLINE);
        Estudiante offline = new Estudiante();
        offline.setEstado(StatusEnum.OFFLINE);
        when(estudianteService.getAllEstudiantes()).thenReturn(Arrays.asList(online, offline));
        mockMvc.perform(get("/api/estudiante/online"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    void testUpdateEstudiante() throws Exception{
       Estudiante updateEstudiante = new Estudiante();
       updateEstudiante.setId(1L);
       updateEstudiante.setNombre("Cataleya");
       updateEstudiante.setApellido("Garcia");
       updateEstudiante.setTelefono("147852369");
       updateEstudiante.setDescripcion("Aprobo curso");
       updateEstudiante.setGrado("9");
       updateEstudiante.setFotoUrl("http://fotoupdate");
       updateEstudiante.setUsuario(testUsuario);
       when(estudianteService.updateEstudiante(eq(1L), any(Estudiante.class))).thenReturn(updateEstudiante);
       mockMvc.perform(put("/api/estudiante/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
               .content(new ObjectMapper().writeValueAsString(updateEstudiante)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value(updateEstudiante.getNombre()));
    }

    @Test
    void registrarEstudiante() throws Exception {
        when(estudianteService.createEstudiante(any(RegistroEstudianteRequest.class))).thenReturn(testEstudiante);
        mockMvc.perform(multipart("/api/estudiante/registro")
                .file((MockMultipartFile) registroEstudianteRequest.getFoto())
                .param("nombre", registroEstudianteRequest.getNombre())
                .param("apellido", registroEstudianteRequest.getApellido())
                .param("telefono", registroEstudianteRequest.getTelefono())
                .param("descripcion", registroEstudianteRequest.getDescripcion())
                .param("grado", registroEstudianteRequest.getGrado())
                .param("correo", registroEstudianteRequest.getCorreo())
                .param("contraseña", registroEstudianteRequest.getContraseña())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testEstudiante.getId()));
    }

    @Test
    void registrarEstudiante_validationError() throws Exception {
        // Simula un error de validación: nombre vacío
        mockMvc.perform(multipart("/api/estudiante/registro")
                .file((MockMultipartFile) registroEstudianteRequest.getFoto())
                .param("nombre", "")
                .param("apellido", registroEstudianteRequest.getApellido())
                .param("telefono", registroEstudianteRequest.getTelefono())
                .param("descripcion", registroEstudianteRequest.getDescripcion())
                .param("grado", registroEstudianteRequest.getGrado())
                .param("correo", registroEstudianteRequest.getCorreo())
                .param("contraseña", registroEstudianteRequest.getContraseña())
        )
        .andExpect(status().isBadRequest());
    }
}
