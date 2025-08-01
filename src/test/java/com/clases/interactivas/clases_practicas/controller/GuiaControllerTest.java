package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Guia;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.GuiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(
        controllers = GuiaController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)
class GuiaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuiaService guiaService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    private Guia guiaTest;
    private Docente docenteTest;

    @BeforeEach
    void setUp(){
        docenteTest = new Docente();
        docenteTest.setId(15L);
        docenteTest.setNombre("Maria");
        docenteTest.setApellido("Gonzalez");
        
        guiaTest = new Guia();
        guiaTest.setId(1L);
        guiaTest.setTitulo("Aprendizaje Prueba");
        guiaTest.setDescripcion("Modo de prueba");
        guiaTest.setUrl("http://guiadeprueba.com");
        guiaTest.setFechaPublicacion(Date.valueOf("2025-07-31"));
        guiaTest.setDocente(docenteTest);
    }
    @Test
    @DisplayName("Debería obtener todas las guías")
    void getAllGuias() throws Exception {
        when(guiaService.getAllGuias()).thenReturn(Collections.singletonList(guiaTest));
        
        mockMvc.perform(get("/api/guia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(guiaTest.getTitulo()))
                .andExpect(jsonPath("$[0].descripcion").value(guiaTest.getDescripcion()));
    }

    @Test
    @DisplayName("Debería obtener guía por ID")
    void getGuiaById() throws Exception {
        when(guiaService.getGuiaById(1L)).thenReturn(guiaTest);
        
        mockMvc.perform(get("/api/guia/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guiaTest.getId()))
                .andExpect(jsonPath("$.titulo").value(guiaTest.getTitulo()));
    }

    @Test
    @DisplayName("Debería devolver 404 cuando la guía no existe")
    void getGuiaByIdNotFound() throws Exception {
        when(guiaService.getGuiaById(1L)).thenReturn(null);
        
        mockMvc.perform(get("/api/guia/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería crear una guía exitosamente")
    void createGuia() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.pdf", 
                "application/pdf", 
                "test content".getBytes()
        );
        
        MockMultipartFile guiaJson = new MockMultipartFile(
                "guia", 
                "", 
                "application/json", 
                new ObjectMapper().writeValueAsString(guiaTest).getBytes()
        );
        
        when(guiaService.createGuia(any(Guia.class), any())).thenReturn(guiaTest);
        
        mockMvc.perform(multipart("/api/guia")
                        .file(file)
                        .file(guiaJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(guiaTest.getTitulo()));
    }

    @Test
    @DisplayName("Debería rechazar archivo con tipo inválido al crear guía")
    void createGuiaInvalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.txt", 
                "text/plain", 
                "test content".getBytes()
        );
        
        MockMultipartFile guiaJson = new MockMultipartFile(
                "guia", 
                "", 
                "application/json", 
                new ObjectMapper().writeValueAsString(guiaTest).getBytes()
        );
        
        mockMvc.perform(multipart("/api/guia")
                        .file(file)
                        .file(guiaJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Solo se permiten archivos PDF y Word"));
    }

    @Test
    @DisplayName("Debería actualizar una guía exitosamente")
    void updateGuia() throws Exception {
        Guia updatedGuia = new Guia();
        updatedGuia.setId(1L);
        updatedGuia.setTitulo("Titulo Actualizado");
        updatedGuia.setDescripcion("Descripcion Actualizada");
        updatedGuia.setUrl("http://nuevaurl.com");
        updatedGuia.setFechaPublicacion(Date.valueOf("2025-08-01"));
        updatedGuia.setDocente(docenteTest);
        
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "updated.pdf", 
                "application/pdf", 
                "updated content".getBytes()
        );
        
        MockMultipartFile guiaJson = new MockMultipartFile(
                "guia", 
                "", 
                "application/json", 
                new ObjectMapper().writeValueAsString(updatedGuia).getBytes()
        );
        
        when(guiaService.updateGuia(eq(1L), any(Guia.class), any())).thenReturn(updatedGuia);
        
        mockMvc.perform(multipart("/api/guia/{id}", 1L)
                        .file(file)
                        .file(guiaJson)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(updatedGuia.getTitulo()));
    }

    @Test
    @DisplayName("Debería devolver 404 al actualizar guía inexistente")
    void updateGuiaNotFound() throws Exception {
        MockMultipartFile guiaJson = new MockMultipartFile(
                "guia", 
                "", 
                "application/json", 
                new ObjectMapper().writeValueAsString(guiaTest).getBytes()
        );
        
        when(guiaService.updateGuia(eq(1L), any(Guia.class), any())).thenReturn(null);
        
        mockMvc.perform(multipart("/api/guia/{id}", 1L)
                        .file(guiaJson)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería eliminar una guía exitosamente")
    void deleteGuia() throws Exception {
        doNothing().when(guiaService).deleteGuia(1L);
        
        mockMvc.perform(delete("/api/guia/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería buscar guías por título")
    void findByTitulo() throws Exception {
        when(guiaService.findByTitulo("Aprendizaje Prueba")).thenReturn(Collections.singletonList(guiaTest));
        
        mockMvc.perform(get("/api/guia/titulo/{titulo}", "Aprendizaje Prueba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(guiaTest.getTitulo()));
    }

    @Test
    @DisplayName("Debería buscar guías por ID de docente")
    void findByDocenteId() throws Exception {
        when(guiaService.findByDocenteId(15L)).thenReturn(Collections.singletonList(guiaTest));
        
        mockMvc.perform(get("/api/guia/docente/{docenteId}", 15L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].docente.id").value(docenteTest.getId()));
    }

    @Test
    @DisplayName("Debería buscar guías por fecha de publicación")
    void findByFechaPublicacion() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 7, 31);
        when(guiaService.findByFechaPublicacion(fecha)).thenReturn(Collections.singletonList(guiaTest));
        
        mockMvc.perform(get("/api/guia/fecha-publicacion/{fechaPublicacion}", "2025-07-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(guiaTest.getTitulo()));
    }

    @Test
    @DisplayName("Debería buscar guías por descripción")
    void findByDescripcion() throws Exception {
        when(guiaService.findByDescripcion("Modo de prueba")).thenReturn(Collections.singletonList(guiaTest));
        
        mockMvc.perform(get("/api/guia/descripcion/{descripcion}", "Modo de prueba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value(guiaTest.getDescripcion()));
    }
}