package com.clases.interactivas.clases_practicas.dto.request;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroDocenteRequest {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;
    private String telefono;
    private String descripcion;
    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "El correo debe ser una direccion de email valida")
    private String correo;
    @NotBlank(message = "La contraseña no puede estar vacio")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contraseña;
    private MultipartFile foto;
}
