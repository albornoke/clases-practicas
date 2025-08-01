package com.clases.interactivas.clases_practicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.clases.interactivas.clases_practicas.enums.CalificacionEnum;
import java.time.LocalDateTime;

@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título de la actividad no puede estar vacío")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El tipo de actividad es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoActividad tipo;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @NotNull(message = "La fecha límite de entrega es obligatoria")
    @Column(name = "fecha_limite", nullable = false)
    private LocalDateTime fechaLimite;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @DecimalMin(value = "0.0", message = "La calificación no puede ser menor a 0")
    @DecimalMax(value = "5.0", message = "La calificación no puede ser mayor a 5")
    @Column(name = "calificacion", precision = 3, scale = 1)
    private Double calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_calificacion")
    private CalificacionEnum nivelCalificacion;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "archivo_url")
    private String archivoUrl;

    @NotNull(message = "El docente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @NotNull(message = "El estudiante es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id")
    private Clase clase;

    @NotBlank(message = "La materia es obligatoria")
    @Column(name = "materia", nullable = false)
    private String materia;

    // Enum para tipos de actividad
    public enum TipoActividad {
        TALLER("Taller"),
        ACTIVIDAD("Actividad"),
        TRABAJO("Trabajo"),
        PROYECTO("Proyecto"),
        ENSAYO("Ensayo");

        private final String descripcion;

        TipoActividad(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    // Constructores
    public Actividad() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Actividad(String titulo, String descripcion, TipoActividad tipo, 
                     LocalDateTime fechaLimite, Docente docente, Estudiante estudiante, String materia) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaLimite = fechaLimite;
        this.docente = docente;
        this.estudiante = estudiante;
        this.materia = materia;
    }

    // Métodos de negocio
    public void calificar(Double nota, String observaciones) {
        this.calificacion = nota;
        this.observaciones = observaciones;
        this.nivelCalificacion = CalificacionEnum.obtenerCalificacion(nota);
        this.aprobado = CalificacionEnum.esAprobado(nota);
    }

    public void marcarComoEntregada(String archivoUrl) {
        this.fechaEntrega = LocalDateTime.now();
        this.archivoUrl = archivoUrl;
    }

    public boolean estaVencida() {
        return LocalDateTime.now().isAfter(this.fechaLimite);
    }

    public boolean estaEntregada() {
        return this.fechaEntrega != null;
    }

    public boolean estaCalificada() {
        return this.calificacion != null;
    }

    public boolean entregadaTarde() {
        return this.fechaEntrega != null && this.fechaEntrega.isAfter(this.fechaLimite);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public TipoActividad getTipo() {
        return tipo;
    }

    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
        if (calificacion != null) {
            this.nivelCalificacion = CalificacionEnum.obtenerCalificacion(calificacion);
            this.aprobado = CalificacionEnum.esAprobado(calificacion);
        }
    }

    public CalificacionEnum getNivelCalificacion() {
        return nivelCalificacion;
    }

    public void setNivelCalificacion(CalificacionEnum nivelCalificacion) {
        this.nivelCalificacion = nivelCalificacion;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}