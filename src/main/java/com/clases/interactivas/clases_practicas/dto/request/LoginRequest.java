package com.clases.interactivas.clases_practicas.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}