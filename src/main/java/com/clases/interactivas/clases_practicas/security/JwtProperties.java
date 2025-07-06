package com.clases.interactivas.clases_practicas.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data // Lombok
public class JwtProperties {
    private String secret;
    private Long expirationMs; // Debe ser Long o long
} 