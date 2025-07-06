package com.clases.interactivas.clases_practicas.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.properties.public") 
@Getter
@Setter
public class ApplicationProperties {
    private String urlApiAcceso;
}
