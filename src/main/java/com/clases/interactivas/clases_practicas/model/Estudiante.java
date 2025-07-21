package com.clases.interactivas.clases_practicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.clases.interactivas.clases_practicas.enums.StatusEnum;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(name = "apellido")
    private String apellido;
    @NotBlank(message = "El telefono no puede estar vacío")
    @Column(name = "telefono")
    private String telefono;
    @NotBlank(message = "El grado no puede estar vacío")
    @Column(name = "grado")
    private String grado;
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser válido")
    @Column(name = "correo")
    private String correo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "foto_url")
    private String fotoUrl;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private StatusEnum estado;

    public Estudiante(){}

    public Estudiante(long id, String nombre, String apellido, String telefono, String descripcion, String grado,
            String fotoUrl, Usuario usuario, StatusEnum estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.grado = grado;
        this.fotoUrl = fotoUrl;
        this.usuario = usuario;
        this.estado = estado;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public StatusEnum getEstado() {
        return estado;
    }

    public void setEstado(StatusEnum estado) {
        this.estado = estado;
    }
}
