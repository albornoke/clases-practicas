-- Scripts SQL para crear las tablas de Examen y Actividad en PostgreSQL
-- Ejecutar estos comandos en tu base de datos PostgreSQL

-- Tabla para Exámenes
CREATE TABLE IF NOT EXISTS examenes (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    materia VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_limite TIMESTAMP NOT NULL,
    fecha_realizacion TIMESTAMP,
    calificacion DECIMAL(3,1) CHECK (calificacion >= 0.0 AND calificacion <= 5.0),
    nivel_calificacion VARCHAR(20) CHECK (nivel_calificacion IN ('BAJO', 'BUENO', 'ALTO', 'SUPERIOR')),
    aprobado BOOLEAN,
    realizado BOOLEAN NOT NULL DEFAULT FALSE,
    observaciones VARCHAR(500),
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duracion_minutos INTEGER CHECK (duracion_minutos > 0),
    docente_id BIGINT NOT NULL,
    estudiante_id BIGINT NOT NULL,
    clase_id BIGINT,
    
    -- Claves foráneas
    CONSTRAINT fk_examen_docente FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE,
    CONSTRAINT fk_examen_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    CONSTRAINT fk_examen_clase FOREIGN KEY (clase_id) REFERENCES clases(id) ON DELETE SET NULL
);

-- Tabla para Actividades (Talleres/Trabajos)
CREATE TABLE IF NOT EXISTS actividades (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('TALLER', 'ACTIVIDAD', 'TRABAJO')),
    materia VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_limite TIMESTAMP NOT NULL,
    fecha_entrega TIMESTAMP,
    calificacion DECIMAL(3,1) CHECK (calificacion >= 0.0 AND calificacion <= 5.0),
    nivel_calificacion VARCHAR(20) CHECK (nivel_calificacion IN ('BAJO', 'BUENO', 'ALTO', 'SUPERIOR')),
    aprobado BOOLEAN,
    entregado BOOLEAN NOT NULL DEFAULT FALSE,
    observaciones VARCHAR(500),
    archivo_url VARCHAR(500),
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    docente_id BIGINT NOT NULL,
    estudiante_id BIGINT NOT NULL,
    clase_id BIGINT,
    
    -- Claves foráneas
    CONSTRAINT fk_actividad_docente FOREIGN KEY (docente_id) REFERENCES docentes(id) ON DELETE CASCADE,
    CONSTRAINT fk_actividad_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    CONSTRAINT fk_actividad_clase FOREIGN KEY (clase_id) REFERENCES clases(id) ON DELETE SET NULL
);

-- Índices para mejorar el rendimiento de las consultas

-- Índices para la tabla examenes
CREATE INDEX IF NOT EXISTS idx_examenes_docente_id ON examenes(docente_id);
CREATE INDEX IF NOT EXISTS idx_examenes_estudiante_id ON examenes(estudiante_id);
CREATE INDEX IF NOT EXISTS idx_examenes_clase_id ON examenes(clase_id);
CREATE INDEX IF NOT EXISTS idx_examenes_materia ON examenes(materia);
CREATE INDEX IF NOT EXISTS idx_examenes_fecha_limite ON examenes(fecha_limite);
CREATE INDEX IF NOT EXISTS idx_examenes_realizado ON examenes(realizado);
CREATE INDEX IF NOT EXISTS idx_examenes_aprobado ON examenes(aprobado);
CREATE INDEX IF NOT EXISTS idx_examenes_nivel_calificacion ON examenes(nivel_calificacion);

-- Índices para la tabla actividades
CREATE INDEX IF NOT EXISTS idx_actividades_docente_id ON actividades(docente_id);
CREATE INDEX IF NOT EXISTS idx_actividades_estudiante_id ON actividades(estudiante_id);
CREATE INDEX IF NOT EXISTS idx_actividades_clase_id ON actividades(clase_id);
CREATE INDEX IF NOT EXISTS idx_actividades_tipo ON actividades(tipo);
CREATE INDEX IF NOT EXISTS idx_actividades_materia ON actividades(materia);
CREATE INDEX IF NOT EXISTS idx_actividades_fecha_limite ON actividades(fecha_limite);
CREATE INDEX IF NOT EXISTS idx_actividades_entregado ON actividades(entregado);
CREATE INDEX IF NOT EXISTS idx_actividades_aprobado ON actividades(aprobado);
CREATE INDEX IF NOT EXISTS idx_actividades_nivel_calificacion ON actividades(nivel_calificacion);

-- Función para actualizar automáticamente la fecha de actualización
CREATE OR REPLACE FUNCTION update_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers para actualizar automáticamente la fecha de actualización
CREATE TRIGGER trigger_examenes_update_fecha
    BEFORE UPDATE ON examenes
    FOR EACH ROW
    EXECUTE FUNCTION update_fecha_actualizacion();

CREATE TRIGGER trigger_actividades_update_fecha
    BEFORE UPDATE ON actividades
    FOR EACH ROW
    EXECUTE FUNCTION update_fecha_actualizacion();

-- Comentarios en las tablas para documentación
COMMENT ON TABLE examenes IS 'Tabla para almacenar información de exámenes asignados por docentes a estudiantes';
COMMENT ON TABLE actividades IS 'Tabla para almacenar talleres, actividades y trabajos asignados por docentes';

COMMENT ON COLUMN examenes.nivel_calificacion IS 'Nivel automático basado en la calificación: BAJO (0.0-2.9), BUENO (3.0-3.7), ALTO (3.8-4.4), SUPERIOR (4.5-5.0)';
COMMENT ON COLUMN actividades.nivel_calificacion IS 'Nivel automático basado en la calificación: BAJO (0.0-2.9), BUENO (3.0-3.7), ALTO (3.8-4.4), SUPERIOR (4.5-5.0)';
COMMENT ON COLUMN examenes.aprobado IS 'TRUE si la calificación es >= 3.0, FALSE si es < 3.0';
COMMENT ON COLUMN actividades.aprobado IS 'TRUE si la calificación es >= 3.0, FALSE si es < 3.0';

-- Consultas de ejemplo para verificar las tablas
-- SELECT * FROM examenes;
-- SELECT * FROM actividades;
-- SELECT COUNT(*) FROM examenes WHERE realizado = false;
-- SELECT COUNT(*) FROM actividades WHERE entregado = false;