package com.clases.interactivas.clases_practicas.dto.response;

import com.clases.interactivas.clases_practicas.model.Usuario;

public class LoginResponseDto {
    private String token;
    private Usuario user;

    public LoginResponseDto(String token, Usuario user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}