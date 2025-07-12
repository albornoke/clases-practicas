import React, { useEffect, useState } from "react";
import { fetchOnlineStudents } from "../services/api";

type Estudiante = {
  id: number;
  nombre: string;
  apellido: string;
  grado: string;
  estado: string;
  fotoUrl?: string;
};

export default function OnlineStudentsList() {
  const [students, setStudents] = useState<Estudiante[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchOnlineStudents()
      .then(setStudents)
      .catch(() => setError("No se pudieron cargar los estudiantes online"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div>Cargando estudiantes en línea...</div>;
  if (error) return <div>{error}</div>;
  if (students.length === 0) return <div>No hay estudiantes en línea.</div>;

  return (
    <div>
      <h2>Estudiantes en línea</h2>
      <ul>
        {students.map((s) => (
          <li key={s.id}>
            {s.fotoUrl && <img src={s.fotoUrl} alt="foto" width={32} height={32} style={{borderRadius: "50%"}} />} {s.nombre} {s.apellido} ({s.grado})
          </li>
        ))}
      </ul>
    </div>
  );
}