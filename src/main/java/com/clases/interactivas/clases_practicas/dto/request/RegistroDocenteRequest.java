package com.clases.interactivas.clases_practicas.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroDocenteRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        private String nombre;

        @NotBlank(message = "El apellido no puede estar vacío")
        private String apellido;

        @NotBlank(message = "El teléfono no puede estar vacío")
        private String telefono;

        @NotBlank(message = "La descripción no puede estar vacía")
        private String descripcion;

        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El correo debe ser una direccion de email valida")
        private String correo;

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, message = "La contraseña debe contar minimo con 8 caracteres")
        private String contraseña;

        @NotNull(message = "La foto es requerida")
        private MultipartFile foto;
}
