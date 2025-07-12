package com.clases.interactivas.clases_practicas.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clase")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @NotNull
    @Column(name = "fecha")
    private Date fecha;
    @NotNull
    @Column(name = "hora_inicio")
    private Time horaInicio;
    @NotNull
    @Column(name = "hora_fin")
    private Time horaFin;
    @NotNull
    @Size(min = 1)
    @Column(name = "tema")
    private String tema;
    @Column(name = "url")
    private String url;
    @NotNull
    @Column(name = "materia")
    private String materia;
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    public Clase(){}

    public Clase(long id, String nombre, String descripcion, Date fecha, Time horaInicio, Time horaFin, String tema,
                 String url, String materia, Docente docente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tema = tema;
        this.url = url;
        this.docente = docente;
        this.materia = materia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre; }

    public String getDescripcion() {return descripcion; }

    public void setDescripcion(String descripcion) {this.descripcion = descripcion; }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

       
}
