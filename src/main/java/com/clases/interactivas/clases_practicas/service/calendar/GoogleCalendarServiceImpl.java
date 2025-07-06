package com.clases.interactivas.clases_practicas.service.calendar;

import com.clases.interactivas.clases_practicas.model.Clase;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private static final String APPLICATION_NAME = "Tu Nombre de Aplicacion"; // Cambia esto
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // Directorio para guardar tokens de acceso

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    // Removed unused constant CREDENTIALS_FILE_PATH_PROPERTY

    @Value("${google.calendar.credentials.file.path}")
    private String credentialsFilePath;

    private com.google.api.services.calendar.Calendar service;

    public GoogleCalendarServiceImpl() throws GeneralSecurityException, IOException {
        // Inicializar el servicio de Calendar aquí puede ser complejo debido al flujo OAuth.
        // Considera inicializarlo bajo demanda o en un método @PostConstruct.
        // Por ahora, lo dejaremos así para simplificar, pero esto necesitará refactorización.
        // this.service = getCalendarService();
    }

    private Credential getCredentials() throws IOException, GeneralSecurityException {
        InputStream in = GoogleCalendarServiceImpl.class.getResourceAsStream(credentialsFilePath.replace("classpath:", ""));
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build(); // Puerto para el callback OAuth
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private com.google.api.services.calendar.Calendar getCalendarServiceInstance() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public String programarClase(Clase clase) throws IOException, GeneralSecurityException {
        if (this.service == null) this.service = getCalendarServiceInstance();
        
        Event event = new Event()
            .setSummary(clase.getTema()) 
            .setDescription(clase.getTema()); // Asumiendo que Clase tiene getDescripcion()

        // Convertir java.sql.Date y java.sql.Time a com.google.api.client.util.DateTime
        // Esto es un ejemplo, necesitarás ajustar la lógica de conversión de fechas y horas
        // según cómo tengas definidas las fechas/horas en tu modelo Clase.
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(clase.getFecha()); // Asumiendo getFecha() devuelve java.util.Date
        
        java.util.Calendar timeCal = java.util.Calendar.getInstance();
        timeCal.setTime(clase.getHoraInicio()); // Asumiendo getHoraInicio() devuelve java.util.Date (o Time)
        
        cal.set(java.util.Calendar.HOUR_OF_DAY, timeCal.get(java.util.Calendar.HOUR_OF_DAY));
        cal.set(java.util.Calendar.MINUTE, timeCal.get(java.util.Calendar.MINUTE));
        cal.set(java.util.Calendar.SECOND, timeCal.get(java.util.Calendar.SECOND));

        DateTime startDateTime = new DateTime(cal.getTime());
        EventDateTime start = new EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("America/Bogota"); // Ajusta la zona horaria
        event.setStart(start);

        // Asumiendo que tienes una hora de fin o duración
        // Aquí, por ejemplo, sumamos una hora a la hora de inicio
        cal.add(java.util.Calendar.HOUR_OF_DAY, 1); 
        DateTime endDateTime = new DateTime(cal.getTime());
        EventDateTime end = new EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("America/Bogota"); // Ajusta la zona horaria
        event.setEnd(end);

        String calendarId = "primary"; // 'primary' para el calendario principal del usuario autenticado
        event = service.events().insert(calendarId, event).execute();
        return event.getId();
    }

    @Override
    public boolean modificarClase(String eventId, Clase clase) throws IOException, GeneralSecurityException {
        if (this.service == null) this.service = getCalendarServiceInstance();
        // Lógica para obtener el evento, modificarlo y actualizarlo
        // Event eventToUpdate = service.events().get("primary", eventId).execute();
        // ... actualizar eventToUpdate con datos de 'clase'
        // service.events().update("primary", eventId, eventToUpdate).execute();
        System.out.println("Modificar clase: " + eventId + " con datos: " + clase.getTema());
        // Implementación pendiente
        return false;
    }

    @Override
    public boolean eliminarClase(String eventId) throws IOException, GeneralSecurityException {
        if (this.service == null) this.service = getCalendarServiceInstance();
        service.events().delete("primary", eventId).execute();
        System.out.println("Eliminar clase: " + eventId);
        return true; // Asumir éxito por ahora
    }
}