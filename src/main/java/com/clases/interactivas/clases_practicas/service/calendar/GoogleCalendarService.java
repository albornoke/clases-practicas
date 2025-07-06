package com.clases.interactivas.clases_practicas.service.calendar;

import com.clases.interactivas.clases_practicas.model.Clase; // Asumiendo que tienes un modelo Clase
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleCalendarService {

    /**
     * Programa una nueva clase en Google Calendar.
     * @param clase La información de la clase a programar.
     * @return El ID del evento creado en Google Calendar, o null si falla.
     * @throws IOException Si ocurre un error de I/O al comunicarse con la API.
     * @throws GeneralSecurityException Si ocurre un error de seguridad.
     */
    String programarClase(Clase clase) throws IOException, GeneralSecurityException;

    /**
     * Modifica una clase existente en Google Calendar.
     * @param eventId El ID del evento en Google Calendar.
     * @param clase La nueva información de la clase.
     * @return true si la modificación fue exitosa, false en caso contrario.
     * @throws IOException Si ocurre un error de I/O.
     * @throws GeneralSecurityException Si ocurre un error de seguridad.
     */
    boolean modificarClase(String eventId, Clase clase) throws IOException, GeneralSecurityException;

    /**
     * Elimina una clase de Google Calendar.
     * @param eventId El ID del evento en Google Calendar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws IOException Si ocurre un error de I/O.
     * @throws GeneralSecurityException Si ocurre un error de seguridad.
     */
    boolean eliminarClase(String eventId) throws IOException, GeneralSecurityException;

    // Podrías agregar más métodos según tus necesidades, como:
    // List<Clase> obtenerClasesProgramadas(Date fechaInicio, Date fechaFin);
}