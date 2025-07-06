package com.clases.interactivas.clases_practicas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class BeanConfig {
    @Bean
    public HttpHeaders httpHeaderToDownload() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=guia.pdf");
        return headers;
    }
}
