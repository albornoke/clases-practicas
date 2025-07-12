package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Clase;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.ClaseService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ClaseController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)
public class ClaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClaseService claseService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    private Clase testClase;
    private Docente testDocente;

    @BeforeEach
    void setUp() {
        testClase = new Clase();
        testClase.setId(1L);
        testClase.setNombre("Matematicas");
        testClase.setDescripcion("Clase de matematicas avanzadas");
        testClase.setFecha(Date.valueOf("2025-07-10"));
        testClase.setHoraInicio(Time.valueOf("08:00:00"));
        testClase.setHoraFin(Time.valueOf("09:00:00"));
        testClase.setTema("Álgebra Lineal");
        testClase.setUrl("http://example.com/matematicas");
        testClase.setMateria("Matemáticas");

        testDocente = new Docente();
        testDocente.setId(10L);
        testDocente.setNombre("Profesor Ejemplo");
        testClase.setDocente(testDocente);
    }

    @Test
    @DisplayName("Debería obtener todas las clases")
    @WithMockUser
    void testGetAllClases() throws Exception {
        when(claseService.getAllClases()).thenReturn(List.of(testClase));

        mockMvc.perform(get("/api/clase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(testClase.getId()))
                .andExpect(jsonPath("$[0].nombre").value(testClase.getNombre()));
        verify(claseService, times(1)).getAllClases();
    }

    @Test
    @DisplayName("Debería obtener clase por ID exitosamente")
    @WithMockUser
    void testGetClaseById() throws Exception {
        when(claseService.getClaseById(1L)).thenReturn(testClase);

        mockMvc.perform(get("/api/clase/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testClase.getId()))
                .andExpect(jsonPath("$.nombre").value(testClase.getNombre()));
        verify(claseService, times(1)).getClaseById(1L);
    }

    @Test
    @DisplayName("Debería devolver 404 Not Found cuando la clase por ID no existe")
    @WithMockUser
    void testGetClaseByIdNotFound() throws Exception {
        when(claseService.getClaseById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/clase/99"))
                .andExpect(status().isNotFound());

        verify(claseService, times(1)).getClaseById(99L);
    }

    @Test
    @DisplayName("Debería crear una clase exitosamente")
    @WithMockUser
    void testCreateClase() throws Exception {
        when(claseService.createClase(any(Clase.class))).thenReturn(testClase);

        String claseJson = "{\"id\":1,\"nombre\":\"Matematicas\",\"descripcion\":\"Clase de matematicas avanzadas\"," +
                "\"fecha\":\"2025-07-10\",\"horaInicio\":\"08:00:00\",\"horaFin\":\"09:00:00\"," +
                "\"tema\":\"Álgebra Lineal\",\"url\":\"http://example.com/matematicas\"," +
                "\"materia\":\"Matemáticas\",\"docente\":{\"id\":10,\"nombre\":\"Profesor Ejemplo\"}}";

        mockMvc.perform(post("/api/clase")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(claseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testClase.getId()))
                .andExpect(jsonPath("$.nombre").value(testClase.getNombre()));

        verify(claseService, times(1)).createClase(any(Clase.class));
    }

    @Test
    @DisplayName("Debería devolver 400 Bad Request al crear clase con argumentos inválidos")
    @WithMockUser
    void testCreateClaseIllegalArgumentException() throws Exception {
        String errorMessage = "Datos de clase inválidos: el nombre no puede ser nulo.";
        when(claseService.createClase(any(Clase.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(post("/api/clase")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));

        verify(claseService, times(1)).createClase(any(Clase.class));
    }

    @Test
    @DisplayName("Debería devolver 500 Internal Server Error al crear clase por error inesperado")
    @WithMockUser
    void testCreateClaseGenericException() throws Exception {
        when(claseService.createClase(any(Clase.class)))
                .thenThrow(new RuntimeException("Error genérico de base de datos"));

        mockMvc.perform(post("/api/clase")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Clase con error inesperado\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al crear la clase"));

        verify(claseService, times(1)).createClase(any(Clase.class));
    }

    @Test
    @DisplayName("Debería actualizar una clase exitosamente")
    @WithMockUser
    void testUpdateClase() throws Exception {
        when(claseService.updateClase(eq(1L), any(Clase.class))).thenReturn(testClase);

        String claseJson = "{\"id\":1,\"nombre\":\"Matematicas Actualizadas\",\"descripcion\":\"Clase actualizada\"," +
                "\"fecha\":\"2025-07-10\",\"horaInicio\":\"08:00:00\",\"horaFin\":\"09:00:00\"," +
                "\"tema\":\"Álgebra Lineal Avanzada\",\"url\":\"http://example.com/matematicas-v2\"," +
                "\"materia\":\"Matemáticas\",\"docente\":{\"id\":10,\"nombre\":\"Profesor Ejemplo\"}}";

        mockMvc.perform(put("/api/clase/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(claseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testClase.getId()))
                .andExpect(jsonPath("$.nombre").value(testClase.getNombre()));

        verify(claseService, times(1)).updateClase(eq(1L), any(Clase.class));
    }

    @Test
    @DisplayName("Debería devolver 400 Bad Request al actualizar clase con argumentos inválidos")
    @WithMockUser
    void testUpdateClaseIllegalArgumentException() throws Exception {
        String errorMessage = "Datos de actualización inválidos: ID no coincide.";
        when(claseService.updateClase(eq(1L), any(Clase.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(put("/api/clase/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":99,\"nombre\":\"Clase Invalida\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));

        verify(claseService, times(1)).updateClase(eq(1L), any(Clase.class));
    }

    @Test
    @DisplayName("Debería devolver 500 Internal Server Error al actualizar clase por error inesperado")
    @WithMockUser
    void testUpdateClaseGenericException() throws Exception {
        when(claseService.updateClase(eq(1L), any(Clase.class)))
                .thenThrow(new RuntimeException("Error inesperado en el servicio al actualizar"));

        mockMvc.perform(put("/api/clase/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nombre\":\"Clase Problematica Actualizacion\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al actualizar la clase"));

        verify(claseService, times(1)).updateClase(eq(1L), any(Clase.class));
    }

    @Test
    @DisplayName("Debería eliminar clase exitosamente")
    @WithMockUser
    void testDeleteClase() throws Exception {
        doNothing().when(claseService).deleteClase(1L);

        mockMvc.perform(delete("/api/clase/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(claseService, times(1)).deleteClase(1L);
    }

    @Test
    @DisplayName("Debería obtener clases por docente")
    @WithMockUser
    void testFindByDocente() throws Exception {
        when(claseService.findByDocente(any(Docente.class))).thenReturn(List.of(testClase));

        mockMvc.perform(get("/api/clase/docente")
                        .param("docenteId", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(testClase.getId()));

        verify(claseService, times(1)).findByDocente(any(Docente.class));
    }

    @Test
    @DisplayName("Debería obtener clases por fecha")
    @WithMockUser
    void testFindByFecha() throws Exception {
        Date fecha = Date.valueOf("2025-07-10");
        when(claseService.findByFecha(fecha)).thenReturn(List.of(testClase));

        mockMvc.perform(get("/api/clase/fecha/" + fecha.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(testClase.getId()));

        verify(claseService, times(1)).findByFecha(fecha);
    }

    @Test
    @DisplayName("Debería obtener clases por hora de inicio")
    @WithMockUser
    void testFindByHoraInicio() throws Exception {
        Time hora = Time.valueOf("13:00:00");
        when(claseService.findByHoraInicio(hora)).thenReturn(List.of(testClase));

        mockMvc.perform(get("/api/clase/hora-inicio/" + hora.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(testClase.getId()));

        verify(claseService, times(1)).findByHoraInicio(hora);
    }
}