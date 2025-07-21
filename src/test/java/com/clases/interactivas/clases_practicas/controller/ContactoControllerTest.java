package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.ContactoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.clases.interactivas.clases_practicas.model.Contacto;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.Collections;

@WebMvcTest(
         controllers = ContactoController.class,
         excludeAutoConfiguration = {
                 SecurityAutoConfiguration.class,
                 UserDetailsServiceAutoConfiguration.class,
                 SecurityFilterAutoConfiguration.class
         }
)
@AutoConfigureMockMvc(addFilters = false)
class ContactoControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @MockBean
   private ContactoService contactoService;
   @MockBean
   private JwtTokenProvider jwtTokenProvider;
   @MockBean
   private CustomUserDetailsService userDetailsService;
   

    @Test
    void getAllContactos() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.getAllContactos()).thenReturn(Arrays.asList(contacto));
        mockMvc.perform(get("/api/contacto"))
                .andExpect(status().isOk());
    }

    @Test
    void getContactoById() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.getContactoById(1L)).thenReturn(contacto);
        mockMvc.perform(get("/api/contacto/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createContacto() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.createContacto(any(Contacto.class))).thenReturn(contacto);
        mockMvc.perform(post("/api/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan\",\"correo\":\"pepito@gmail.com\",\"telefono\":\"123456789\",\"mensaje\":\"Prueba de creacion\",\"asunto\":\"Interes\"}"))
                .andExpect(status().isOk());
        assertNotNull(contacto.getFecha());
        assertNotNull(contacto.getHora());
    }

    @Test
    void updateContacto() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.updateContacto(eq(1L), any(Contacto.class))).thenReturn(contacto);
        mockMvc.perform(put("/api/contacto/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContacto() throws Exception {
        doNothing().when(contactoService).deleteContacto(1L);
        mockMvc.perform(delete("/api/contacto/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByCorreo() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.findByCorreo("correo@ejemplo.com")).thenReturn(Collections.singletonList(contacto));
        mockMvc.perform(get("/api/contacto/correo/correo@ejemplo.com"))
                .andExpect(status().isOk());
    }

    @Test
    void findByTelefono() throws Exception {
        Contacto contacto = new Contacto();
        when(contactoService.findByTelefono("123456789")).thenReturn(Collections.singletonList(contacto));
        mockMvc.perform(get("/api/contacto/telefono/123456789"))
                .andExpect(status().isOk());
    }
//
//    @Test
//    void testGetContactoById_contentVerification() throws Exception {
//        mockMvc.perform(get("/api/contacto/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.nombre").isNotEmpty());
//    }

    @Test
    void testGetContactoById_notFound() throws Exception {
        // Suponiendo que no existe el contacto con ID 9999
        mockMvc.perform(get("/api/contacto/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateContacto_invalidInput() throws Exception {
        String contactoJson = "{\"nombre\":\"\",\"correo\":\"\",\"mensaje\":\"\",\"telefono\":\"\",\"asunto\":\"\"}";
        mockMvc.perform(post("/api/contacto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contactoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateContacto_notFound() throws Exception {
        String contactoJson = "{\"nombre\":\"Nuevo\",\"correo\":\"nuevo@mail.com\",\"mensaje\":\"Hola\",\"telefono\":\"123456\",\"asunto\":\"Clases\"}";
        mockMvc.perform(put("/api/contacto/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contactoJson))
                .andExpect(status().isNotFound());
    }
}