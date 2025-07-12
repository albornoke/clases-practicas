package com.clases.interactivas.clases_practicas.controller;

import com.clases.interactivas.clases_practicas.model.Usuario;
import com.clases.interactivas.clases_practicas.service.impl.UsuarioService;
import com.clases.interactivas.clases_practicas.dto.request.LoginRequest;
import com.clases.interactivas.clases_practicas.dto.response.LoginResponseDto; // Asegúrate de importar LoginResponse
import com.clases.interactivas.clases_practicas.security.JwtTokenProvider; // Asegúrate de importar JwtTokenProvider

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@Validated 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder; // Inyecta PasswordEncoder

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Usuario usuario = usuarioService.autenticarUsuario(email, password);        
        String token = tokenProvider.generateToken(usuario);        
        return ResponseEntity.ok(new LoginResponseDto(token, usuario));
    }

    @GetMapping("/generar-hash")
    public ResponseEntity<String> generarHash(@RequestParam String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return ResponseEntity.ok(hashedPassword);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(usuarioService.obtenerPorRol(rol));
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerPerfil(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenProvider.getEmailFromToken(token);
        Usuario usuario = usuarioService.obtenerPorEmail(email)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }
}
