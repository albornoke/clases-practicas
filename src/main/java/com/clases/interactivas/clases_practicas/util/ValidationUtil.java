package com.clases.interactivas.clases_practicas.util;

import java.sql.Time;
import java.net.URL;
import java.net.MalformedURLException;

public class ValidationUtil {

    /**
     * Verifica si dos rangos de tiempo se solapan
     * @param start1 Hora de inicio del primer rango
     * @param end1 Hora de fin del primer rango
     * @param start2 Hora de inicio del segundo rango
     * @param end2 Hora de fin del segundo rango
     * @return true si hay solapamiento, false en caso contrario
     */
    public static boolean isTimeOverlap(Time start1, Time end1, Time start2, Time end2) {
        // Compara si el inicio de un rango está antes del fin del otro rango y viceversa
        return !start1.after(end2) && !start2.after(end1);
    }

    /**
     * Valida si una cadena es una URL válida
     * @param url Cadena a validar
     * @return true si es una URL válida, false en caso contrario
     */
    @SuppressWarnings("deprecation")
    public static boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}