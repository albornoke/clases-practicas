package com.clases.interactivas.clases_practicas.model;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre")
    private String nombre;
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de email válida")
    @Column(name = "correo")
    private String correo;
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(name = "mensaje")
    private String mensaje;
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "hora")
    private String hora;
    @NotBlank
    @Column(name = "asunto")
    private String asunto;

    public Contacto(){
        this.fecha = LocalDate.now().toString();
        this.hora = LocalTime.now().toString();
    }

    public Contacto(long id, String nombre, String correo, String mensaje, String telefono, String fecha, String hora, String asunto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.mensaje = mensaje;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.asunto = asunto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public  String getAsunto() {return asunto; }

    public void setAsunto(String asunto) {this.asunto = asunto; }

    
    
}
