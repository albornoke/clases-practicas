package com.clases.interactivas.clases_practicas.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum CalificacionEnum {
    BAJO(0.0, 2.9, "Bajo", "Perdió"),
    BUENO(3.0, 3.7, "Bueno", "Aprobó"),
    ALTO(3.8, 4.4, "Alto", "Aprobó"),
    SUPERIOR(4.5, 5.0, "Superior", "Aprobó");

    private final Double rangoMinimo;
    private final Double rangoMaximo;
    private final String descripcion;
    private final String estado;

    CalificacionEnum(Double rangoMinimo, Double rangoMaximo, String descripcion, String estado) {
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public static CalificacionEnum obtenerCalificacion(Double nota) {
        if (nota == null) {
            return null;
        }
        
        return Arrays.stream(CalificacionEnum.values())
                .filter(c -> nota >= c.getRangoMinimo() && nota <= c.getRangoMaximo())
                .findFirst()
                .orElse(BAJO);
    }

    public static boolean esAprobado(Double nota) {
        if (nota == null || nota < 3.0) {
            return false;
        }
        CalificacionEnum calificacion = obtenerCalificacion(nota);
        return calificacion != null && !calificacion.equals(BAJO);
    }

    public static String obtenerEstado(Double nota) {
        CalificacionEnum calificacion = obtenerCalificacion(nota);
        return calificacion != null ? calificacion.getEstado() : "Perdió";
    }
}