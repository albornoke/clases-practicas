package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.config.SecurityConfig;
import com.clases.interactivas.clases_practicas.controller.UsuarioController;
import com.clases.interactivas.clases_practicas.model.Docente;
import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import com.clases.interactivas.clases_practicas.dto.request.LoginRequest;
import com.clases.interactivas.clases_practicas.dto.response.LoginResponseDto;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtAuthenticationFilter;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)

public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Usuario usuario;
    private Docente docente;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");
        usuario.setPassword("hashedPassword");
        usuario.setRol("ESTUDIANTE");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getEmailFromToken(anyString())).thenReturn("test@example.com");
        when(customUserDetailsService.loadUserByUsername(anyString()))
            .thenReturn(new org.springframework.security.core.userdetails.User(
                "test@example.com", "password", Collections.emptyList()));
    }

    @Test
    @DisplayName("Debería registrar un usuario exitosamente")
    @WithMockUser
    void testRegistrarUsuario() throws Exception {

        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"email\":\"test@example.com\",\"password\":\"hashedPassword\",\"rol\":\"ESTUDIANTE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.rol").value("ESTUDIANTE"));

        verify(usuarioService).registrarUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería autenticar usuario y devolver token")
    @WithMockUser
    void testLogin() throws Exception {

        String token = "jwt-token-example";
        LoginResponseDto loginResponse = new LoginResponseDto(token, usuario);
        
        when(usuarioService.autenticarUsuario(anyString(), anyString())).thenReturn(usuario);
        when(jwtTokenProvider.generateToken(any(Usuario.class))).thenReturn(token);

        mockMvc.perform(post("/api/usuarios/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.usuario.email").value("test@example.com"));

        verify(usuarioService).autenticarUsuario("test@example.com", "password123");
        verify(jwtTokenProvider).generateToken(usuario);
    }

    @Test
    @DisplayName("Debería obtener todos los usuarios")
    @WithMockUser
    void testObtenerTodos() throws Exception {

        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.obtenerTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        verify(usuarioService).obtenerTodos();
    }

    @Test
    @DisplayName("Debería obtener usuario por ID")
    @WithMockUser
    void testObtenerPorId() throws Exception {

        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(usuarioService).obtenerPorId(1L);
    }

    @Test
    @DisplayName("Debería devolver 404 cuando usuario no existe")
    @WithMockUser
    void testObtenerPorIdNoEncontrado() throws Exception {

        when(usuarioService.obtenerPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());

        verify(usuarioService).obtenerPorId(999L);
    }

    @Test
    @DisplayName("Debería actualizar usuario exitosamente")
    @WithMockUser
    void testActualizarUsuario() throws Exception {

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setEmail("updated@example.com");
        usuarioActualizado.setRol("DOCENTE");

        when(usuarioService.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuarioActualizado);

        mockMvc.perform(put("/api/usuarios/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"email\":\"updated@example.com\",\"rol\":\"DOCENTE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.rol").value("DOCENTE"));

        verify(usuarioService).actualizarUsuario(eq(1L), any(Usuario.class));
    }

    @Test
    @DisplayName("Debería eliminar usuario exitosamente")
    @WithMockUser
    void testEliminarUsuario() throws Exception {

        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(usuarioService).eliminarUsuario(1L);
    }

    @Test
    @DisplayName("Debería obtener usuarios por rol")
    @WithMockUser
    void testObtenerPorRol() throws Exception {

        List<Usuario> estudiantes = Arrays.asList(usuario);
        when(usuarioService.obtenerPorRol("ESTUDIANTE")).thenReturn(estudiantes);

        mockMvc.perform(get("/api/usuarios/rol/ESTUDIANTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].rol").value("ESTUDIANTE"));

        verify(usuarioService).obtenerPorRol("ESTUDIANTE");
    }

    @Test
    @DisplayName("Debería generar hash de contraseña")
    @WithMockUser
    void testGenerarHash() throws Exception {

        String password = "myPassword";
        String hashedPassword = "$2a$10$hashedPassword";
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);

        mockMvc.perform(get("/api/usuarios/generar-hash")
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string(hashedPassword));

        verify(passwordEncoder).encode(password);
    }

    @Test
    @DisplayName("Debería obtener perfil del usuario autenticado")
    @WithMockUser
    void testObtenerPerfil() throws Exception {

        String token = "Bearer jwt-token";
        String email = "test@example.com";
        
        when(jwtTokenProvider.getEmailFromToken("jwt-token")).thenReturn(email);
        when(usuarioService.obtenerPorEmail(email)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/me")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));

        verify(jwtTokenProvider).getEmailFromToken("jwt-token");
        verify(usuarioService).obtenerPorEmail(email);
    }
}
