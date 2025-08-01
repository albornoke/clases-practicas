package com.clases.interactivas.clases_practicas.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "guia")
public class Guia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "titulo")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    @Column(name = "descripcion")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    @Column(name = "url")
    private String url;
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false) 

    private Docente docente;

    public Guia(){
        this.fechaPublicacion = Date.valueOf(LocalDate.now());
    }

    public Guia(long id, String titulo, String descripcion, String url, Date fechaPublicacion, Docente docente) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url = url;
        this.fechaPublicacion = fechaPublicacion;
        this.docente = docente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

  
    
    
}
