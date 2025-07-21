package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.dto.request.LoginRequest;
import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.security.CustomUserDetailsService;
import com.clases.interactivas.clases_practicas.security.JwtAuthenticationFilter;
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
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
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private Usuario usuario;
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
        
        when(usuarioService.autenticarUsuario(anyString(), anyString())).thenReturn(usuario);
        when(jwtTokenProvider.generateToken(any(Usuario.class))).thenReturn(token);

        mockMvc.perform(post("/api/usuarios/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.user.email").value("test@example.com"));

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
    @DisplayName("Debería devolver 401 cuando el token JWT es inválido")
    void testAccesoConJwtInvalido() throws Exception {
        when(jwtTokenProvider.validateToken("token_invalido")).thenReturn(false);

        mockMvc.perform(get("/api/usuarios/perfil")
                        .header("Authorization", "Bearer token_invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Debería devolver 401 cuando el token JWT está expirado")
    void testAccesoConJwtExpirado() throws Exception {
        when(jwtTokenProvider.validateToken("token_expirado"))
                .thenThrow(new ExpiredJwtException(null, null, "Token expirado"));

        mockMvc.perform(get("/api/usuarios/perfil")
                        .header("Authorization", "Bearer token_expirado"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Debería devolver 404 cuando el token es válido pero el usuario no existe")
    void testAccesoConTokenValidoPeroUsuarioNoExiste() throws Exception {
        when(jwtTokenProvider.validateToken("token_valido")).thenReturn(true);
        when(jwtTokenProvider.getEmailFromToken("token_valido")).thenReturn("noexiste@example.com");
        when(usuarioService.obtenerPorEmail("noexiste@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/perfil")
                        .header("Authorization", "Bearer token_valido"))
                .andExpect(status().isNotFound());
    }

}
