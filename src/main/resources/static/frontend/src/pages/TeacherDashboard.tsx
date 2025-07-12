import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Users, Clock, CheckCircle, AlertCircle, Calendar, BookOpen, Video, LogOut } from "lucide-react";
import { Link } from "react-router-dom";
import { ReviewAssignmentModal } from "@/components/ReviewAssignmentModal";
import { GradeAssignmentModal } from "@/components/GradeAssignmentModal";
import { useState } from "react";
import OnlineStudentsList from "../components/OnlineStudentsList";

const TeacherDashboard = () => {
  const [selectedAssignment, setSelectedAssignment] = useState<any>(null);
  const [showReviewModal, setShowReviewModal] = useState(false);
  const [showGradeModal, setShowGradeModal] = useState(false);

  const estudiantesOnline = [
    { id: 1, nombre: "Juan Pérez", materia: "Matemáticas", avatar: "/placeholder.svg", tiempo: "45 min" },
    { id: 2, nombre: "María García", materia: "Español", avatar: "/placeholder.svg", tiempo: "32 min" },
    { id: 3, nombre: "Carlos López", materia: "Ciencias", avatar: "/placeholder.svg", tiempo: "18 min" },
    { id: 4, nombre: "Ana Torres", materia: "Historia", avatar: "/placeholder.svg", tiempo: "12 min" },
    { id: 5, nombre: "Luis Martín", materia: "Matemáticas", avatar: "/placeholder.svg", tiempo: "8 min" }
  ];

  const horarioHoy = [
    { hora: "09:00", materia: "Matemáticas", estudiante: "Juan Pérez", tipo: "Virtual", estado: "Completada" },
    { hora: "10:30", materia: "Español", estudiante: "María García", tipo: "Presencial", estado: "En curso" },
    { hora: "14:00", materia: "Ciencias", estudiante: "Carlos López", tipo: "Virtual", estado: "Próxima" },
    { hora: "15:30", materia: "Historia", estudiante: "Ana Torres", tipo: "Presencial", estado: "Próxima" },
    { hora: "17:00", materia: "Matemáticas", estudiante: "Luis Martín", tipo: "Virtual", estado: "Próxima" }
  ];

  const actividadesPendientes = [
    { id: 1, estudiante: "Juan Pérez", actividad: "Examen de Álgebra", materia: "Matemáticas", fechaEntrega: "2024-06-10", tipo: "Examen" },
    { id: 2, estudiante: "María García", actividad: "Ensayo sobre Cervantes", materia: "Español", fechaEntrega: "2024-06-09", tipo: "Ensayo" },
    { id: 3, estudiante: "Carlos López", actividad: "Proyecto de Física", materia: "Ciencias", fechaEntrega: "2024-06-12", tipo: "Proyecto" },
    { id: 4, estudiante: "Ana Torres", actividad: "Línea de Tiempo", materia: "Historia", fechaEntrega: "2024-06-11", tipo: "Tarea" },
    { id: 5, estudiante: "Luis Martín", actividad: "Problemas de Geometría", materia: "Matemáticas", fechaEntrega: "2024-06-09", tipo: "Tarea" }
  ];

  const estadisticas = {
    estudiantesTotal: 24,
    clasesHoy: 5,
    actividadesPendientes: actividadesPendientes.length
  };

  const handleReviewAssignment = (assignment: any) => {
    setSelectedAssignment(assignment);
    setShowReviewModal(true);
  };

  const handleGradeAssignment = (assignment: any) => {
    setSelectedAssignment(assignment);
    setShowGradeModal(true);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-blue-50 p-6">
      <div className="max-w-7xl mx-auto space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Panel del Docente</h1>
            <p className="text-gray-600">Bienvenida, Profesora María</p>
          </div>
          <div className="flex gap-2">
            <Button className="bg-purple-600 hover:bg-purple-700">
              <Video className="h-4 w-4 mr-2" />
              Iniciar Clase Virtual
            </Button>
            <Button variant="outline">
              <Calendar className="h-4 w-4 mr-2" />
              Ver Calendario
            </Button>
            <Link to="/login">
              <Button variant="outline">
                <LogOut className="h-4 w-4 mr-2" />
                Salir
              </Button>
            </Link>
          </div>
        </div>

        {/* Cards de estadísticas */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Estudiantes Total</CardTitle>
              <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{estadisticas.estudiantesTotal}</div>
              <p className="text-xs text-muted-foreground">En todas las materias</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Online Ahora</CardTitle>
              <div className="h-2 w-2 bg-green-500 rounded-full animate-pulse"></div>
            </CardHeader>
            <CardContent>
              {/* Puedes mostrar el componente OnlineStudentsList aquí para contar los online si lo deseas */}
              <OnlineStudentsList />
              <p className="text-xs text-muted-foreground mt-2">Estudiantes conectados</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Clases Hoy</CardTitle>
              <Clock className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{estadisticas.clasesHoy}</div>
              <p className="text-xs text-muted-foreground">Programadas para hoy</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Por Calificar</CardTitle>
              <AlertCircle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-orange-600">{estadisticas.actividadesPendientes}</div>
              <p className="text-xs text-muted-foreground">Actividades pendientes</p>
            </CardContent>
          </Card>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Estudiantes Online */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <div className="h-2 w-2 bg-green-500 rounded-full animate-pulse"></div>
                Estudiantes Online
              </CardTitle>
              <CardDescription>Estudiantes conectados en tiempo real</CardDescription>
            </CardHeader>
            <CardContent>
              {/* Aquí también puedes mostrar la lista detallada */}
              <OnlineStudentsList />
            </CardContent>
          </Card>

          {/* Horario de Hoy */}
          <Card>
            <CardHeader>
              <CardTitle>Horario de Hoy</CardTitle>
              <CardDescription>Clases programadas para hoy</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {horarioHoy.map((clase, index) => (
                  <div key={index} className="flex items-center justify-between p-3 border rounded-lg">
                    <div className="flex items-center space-x-3">
                      <div className="text-sm font-mono">{clase.hora}</div>
                      <div>
                        <p className="font-medium text-sm">{clase.materia}</p>
                        <p className="text-xs text-gray-500">{clase.estudiante}</p>
                      </div>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Badge variant={clase.tipo === "Virtual" ? "default" : "secondary"}>
                        {clase.tipo}
                      </Badge>
                      <Badge 
                        variant={
                          clase.estado === "Completada" ? "default" : 
                          clase.estado === "En curso" ? "destructive" : "outline"
                        }
                      >
                        {clase.estado}
                      </Badge>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Actividades Pendientes por Calificar */}
        <Card>
          <CardHeader>
            <CardTitle>Actividades Pendientes por Calificar</CardTitle>
            <CardDescription>Actividades entregadas esperando calificación</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Estudiante</TableHead>
                  <TableHead>Actividad</TableHead>
                  <TableHead>Materia</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Fecha Entrega</TableHead>
                  <TableHead>Acciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {actividadesPendientes.map((actividad) => (
                  <TableRow key={actividad.id}>
                    <TableCell className="font-medium">{actividad.estudiante}</TableCell>
                    <TableCell>{actividad.actividad}</TableCell>
                    <TableCell>{actividad.materia}</TableCell>
                    <TableCell>
                      <Badge variant="outline">{actividad.tipo}</Badge>
                    </TableCell>
                    <TableCell>{actividad.fechaEntrega}</TableCell>
                    <TableCell>
                      <div className="flex space-x-2">
                        <Button 
                          variant="outline" 
                          size="sm"
                          onClick={() => handleReviewAssignment(actividad)}
                        >
                          Revisar
                        </Button>
                        <Button 
                          size="sm"
                          onClick={() => handleGradeAssignment(actividad)}
                        >
                          Calificar
                        </Button>
                      </div>
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
          <ReviewAssignmentModal
            isOpen={showReviewModal}
            onClose={() => setShowReviewModal(false)}
            assignment={selectedAssignment}
          />
          <GradeAssignmentModal
            isOpen={showGradeModal}
            onClose={() => setShowGradeModal(false)}
            assignment={selectedAssignment}
          />
        </>
      )}
    </div>
  );
};

export default TeacherDashboard;
