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

@Entity
@Table(name = "clase")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "hora_inicio")
    private Time horaInicio;
    @Column(name = "hora_fin")
    private Time horaFin;
    @Column(name = "tema")
    private String tema;
    @Column(name = "url")
    private String url;
    @Column(name = "materia")
    private String materia;
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false) // Asegúrate que la columna referenciada exista y considera nullable=false si una clase siempre debe tener un docente.
    private Docente docente;

    public Clase(){}

    public Clase(long id, Date fecha, Time horaInicio, Time horaFin, String tema, String url,
            String materia, Docente docente) {
        this.id = id;
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

    public void setEnlace_reunion(String url) {
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
