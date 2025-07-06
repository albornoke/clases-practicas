import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { BookOpen, Clock, CheckCircle, AlertCircle, Calendar, Play, FileText, X, LogOut } from "lucide-react";
import { Link } from "react-router-dom";
import { SubmitAssignmentModal } from "@/components/SubmitAssignmentModal";
import { ViewAssignmentModal } from "@/components/ViewAssignmentModal";
import { useState } from "react";
import { useAuth } from "@/hooks/useAuth"; // Import useAuth

const StudentDashboard = () => {
  const { logout } = useAuth(); // Get logout function
  const [selectedAssignment, setSelectedAssignment] = useState<any>(null);
  const [showSubmitModal, setShowSubmitModal] = useState(false);
  const [showViewModal, setShowViewModal] = useState(false);

  const materiasEnDesarrollo = [
    { id: 1, nombre: "Matemáticas", profesor: "Prof. María López", progreso: 75, proximaClase: "2024-06-09 10:00", tareasPendientes: 2 },
    { id: 2, nombre: "Español", profesor: "Prof. Ana García", progreso: 60, proximaClase: "2024-06-10 14:00", tareasPendientes: 1 },
    { id: 3, nombre: "Ciencias", profesor: "Prof. Carlos Ruiz", progreso: 45, proximaClase: "2024-06-11 09:00", tareasPendientes: 3 },
    { id: 4, nombre: "Historia", profesor: "Prof. Laura Díaz", progreso: 80, proximaClase: "2024-06-12 15:00", tareasPendientes: 0 }
  ];

  const materiasFinalizadas = [
    { id: 1, nombre: "Inglés Básico", profesor: "Prof. John Smith", calificacion: 95, fechaFinalizacion: "2024-05-15" },
    { id: 2, nombre: "Geografía", profesor: "Prof. Elena Morales", calificacion: 88, fechaFinalizacion: "2024-04-20" },
    { id: 3, nombre: "Arte", profesor: "Prof. Miguel Torres", calificacion: 92, fechaFinalizacion: "2024-03-30" }
  ];

  const horarioSemana = [
    { dia: "Lunes", hora: "10:00", materia: "Matemáticas", profesor: "Prof. María López", tipo: "Virtual" },
    { dia: "Martes", hora: "14:00", materia: "Español", profesor: "Prof. Ana García", tipo: "Presencial" },
    { dia: "Miércoles", hora: "09:00", materia: "Ciencias", profesor: "Prof. Carlos Ruiz", tipo: "Virtual" },
    { dia: "Jueves", hora: "15:00", materia: "Historia", profesor: "Prof. Laura Díaz", tipo: "Presencial" },
    { dia: "Viernes", hora: "11:00", materia: "Matemáticas", profesor: "Prof. María López", tipo: "Virtual" }
  ];

  const actividadesPendientes = [
    { id: 1, titulo: "Examen de Álgebra", materia: "Matemáticas", fechaVencimiento: "2024-06-10", tipo: "Examen", prioridad: "Alta" },
    { id: 2, titulo: "Ensayo sobre Cervantes", materia: "Español", fechaVencimiento: "2024-06-12", tipo: "Ensayo", prioridad: "Media" },
    { id: 3, titulo: "Experimento de Física", materia: "Ciencias", fechaVencimiento: "2024-06-15", tipo: "Práctica", prioridad: "Media" },
    { id: 4, titulo: "Investigación sobre la Independencia", materia: "Historia", fechaVencimiento: "2024-06-18", tipo: "Investigación", prioridad: "Baja" }
  ];

  const faltas = [
    { fecha: "2024-06-05", materia: "Matemáticas", motivo: "Enfermedad", justificada: true },
    { fecha: "2024-06-03", materia: "Ciencias", motivo: "Cita médica", justificada: true },
    { fecha: "2024-05-28", materia: "Español", motivo: "Sin justificar", justificada: false },
    { fecha: "2024-05-25", materia: "Historia", motivo: "Emergencia familiar", justificada: true }
  ];

  const estadisticas = {
    materiasActivas: materiasEnDesarrollo.length,
    materiasCompletadas: materiasFinalizadas.length,
    actividadesPendientes: actividadesPendientes.length,
    totalFaltas: faltas.length
  };

  const handleSubmitAssignment = (assignment: any) => {
    setSelectedAssignment(assignment);
    setShowSubmitModal(true);
  };

  const handleViewAssignment = (assignment: any) => {
    setSelectedAssignment(assignment);
    setShowViewModal(true);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 via-white to-blue-50 p-6">
      <div className="max-w-7xl mx-auto space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Mi Portal Estudiantil</h1>
            <p className="text-gray-600">Hola Juan, ¡continuemos aprendiendo!</p>
          </div>
          <div className="flex gap-2">
            <Button className="bg-green-600 hover:bg-green-700">
              <Play className="h-4 w-4 mr-2" />
              Clase en Vivo
            </Button>
            <Button variant="outline">
              <Calendar className="h-4 w-4 mr-2" />
              Mi Calendario
            </Button>
            <Button variant="outline" onClick={logout}> {/* Use onClick for logout */}
              <LogOut className="h-4 w-4 mr-2" />
              Salir
            </Button>
          </div>
        </div>

        {/* Cards de estadísticas */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Materias Activas</CardTitle>
              <BookOpen className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{estadisticas.materiasActivas}</div>
              <p className="text-xs text-muted-foreground">En desarrollo</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Completadas</CardTitle>
              <CheckCircle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-green-600">{estadisticas.materiasCompletadas}</div>
              <p className="text-xs text-muted-foreground">Materias finalizadas</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Tareas Pendientes</CardTitle>
              <AlertCircle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-orange-600">{estadisticas.actividadesPendientes}</div>
              <p className="text-xs text-muted-foreground">Por completar</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Faltas</CardTitle>
              <X className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-red-600">{estadisticas.totalFaltas}</div>
              <p className="text-xs text-muted-foreground">Este período</p>
            </CardContent>
          </Card>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Materias en Desarrollo */}
          <Card>
            <CardHeader>
              <CardTitle>Materias en Desarrollo</CardTitle>
              <CardDescription>Tu progreso actual en cada materia</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {materiasEnDesarrollo.map((materia) => (
                  <div key={materia.id} className="p-4 border rounded-lg">
                    <div className="flex justify-between items-start mb-2">
                      <div>
                        <h4 className="font-medium">{materia.nombre}</h4>
                        <p className="text-sm text-gray-500">{materia.profesor}</p>
                      </div>
                      <Badge variant="outline">{materia.tareasPendientes} tareas</Badge>
                    </div>
                    <div className="mb-2">
                      <div className="flex justify-between text-sm mb-1">
                        <span>Progreso</span>
                        <span>{materia.progreso}%</span>
                      </div>
                      <Progress value={materia.progreso} className="h-2" />
                    </div>
                    <p className="text-xs text-gray-500">
                      Próxima clase: {materia.proximaClase}
                    </p>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Horario de la Semana */}
          <Card>
            <CardHeader>
              <CardTitle>Horario de Clases</CardTitle>
              <CardDescription>Tu horario semanal</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {horarioSemana.map((clase, index) => (
                  <div key={index} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <div className="text-sm font-medium w-16">{clase.dia}</div>
                      <div className="text-sm font-mono">{clase.hora}</div>
                      <div>
                        <p className="font-medium text-sm">{clase.materia}</p>
                        <p className="text-xs text-gray-500">{clase.profesor}</p>
                      </div>
                    </div>
                    <Badge variant={clase.tipo === "Virtual" ? "default" : "secondary"}>
                      {clase.tipo}
                    </Badge>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Materias Finalizadas */}
        <Card>
          <CardHeader>
            <CardTitle>Materias Finalizadas</CardTitle>
            <CardDescription>Materias que has completado exitosamente</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              {materiasFinalizadas.map((materia) => (
                <div key={materia.id} className="p-4 border rounded-lg">
                  <div className="flex justify-between items-start mb-2">
                    <div>
                      <h4 className="font-medium">{materia.nombre}</h4>
                      <p className="text-sm text-gray-500">{materia.profesor}</p>
                    </div>
                    <Badge variant="default" className="bg-green-100 text-green-800">
                      {materia.calificacion}%
                    </Badge>
                  </div>
                  <p className="text-xs text-gray-500">
                    Finalizada: {materia.fechaFinalizacion}
                  </p>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        {/* Actividades por Realizar */}
        <Card>
          <CardHeader>
            <CardTitle>Actividades por Realizar</CardTitle>
            <CardDescription>Tareas y proyectos pendientes</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Actividad</TableHead>
                  <TableHead>Materia</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Vencimiento</TableHead>
                  <TableHead>Prioridad</TableHead>
                  <TableHead>Acciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {actividadesPendientes.map((actividad) => (
                  <TableRow key={actividad.id}>
                    <TableCell className="font-medium">{actividad.titulo}</TableCell>
                    <TableCell>{actividad.materia}</TableCell>
                    <TableCell>
                      <Badge variant="outline">{actividad.tipo}</Badge>
                    </TableCell>
                    <TableCell>{actividad.fechaVencimiento}</TableCell>
                    <TableCell>
                      <Badge 
                        variant={
                          actividad.prioridad === "Alta" ? "destructive" : 
                          actividad.prioridad === "Media" ? "default" : "secondary"
                        }
                      >
                        {actividad.prioridad}
                      </Badge>
                    </TableCell>
                    <TableCell>
                      <div className="flex space-x-2">
                        <Button 
                          variant="outline" 
                          size="sm"
                          onClick={() => handleViewAssignment(actividad)}
                        >
                          <FileText className="h-4 w-4 mr-1" />
                          Ver
                        </Button>
                        <Button 
                          size="sm"
                          onClick={() => handleSubmitAssignment(actividad)}
                        >
                          Entregar
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        {/* Faltas a Clases */}
        <Card>
          <CardHeader>
            <CardTitle>Registro de Faltas</CardTitle>
            <CardDescription>Historial de ausencias a clases</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Fecha</TableHead>
                  <TableHead>Materia</TableHead>
                  <TableHead>Motivo</TableHead>
                  <TableHead>Estado</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {faltas.map((falta, index) => (
                  <TableRow key={index}>
                    <TableCell>{falta.fecha}</TableCell>
                    <TableCell>{falta.materia}</TableCell>
                    <TableCell>{falta.motivo}</TableCell>
                    <TableCell>
                      <Badge variant={falta.justificada ? "default" : "destructive"}>
                        {falta.justificada ? "Justificada" : "Sin justificar"}
                      </Badge>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      {/* Modales */}
      {selectedAssignment && (
        <>
          <SubmitAssignmentModal
            isOpen={showSubmitModal}
            onClose={() => setShowSubmitModal(false)}
            assignment={selectedAssignment}
          />
          <ViewAssignmentModal
            isOpen={showViewModal}
            onClose={() => setShowViewModal(false)}
            assignment={selectedAssignment}
          />
        </>
      )}
    </div>
  );
};

export default StudentDashboard;
