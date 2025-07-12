import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "@/components/ui/chart";
import { BarChart, Bar, LineChart, Line, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, ResponsiveContainer } from "recharts";
import { AlertTriangle, Users, BookOpen, Clock, TrendingUp, FileText, LogOut, GraduationCap, Shield, UserPlus } from "lucide-react";
import { Link } from "react-router-dom";
import { ReportDetailsModal } from "@/components/ReportDetailsModal";
import { GenerateReportModal } from "@/components/GenerateReportModal";
import { useState } from "react";

const AdminDashboard = () => {
  const [selectedReport, setSelectedReport] = useState<any>(null);
  const [showReportDetails, setShowReportDetails] = useState(false);
  const [showGenerateReport, setShowGenerateReport] = useState(false);

  // Datos de ejemplo para las gráficas
  const reportesData = [
    { mes: "Ene", reportes: 12, resueltos: 8 },
    { mes: "Feb", reportes: 19, resueltos: 15 },
    { mes: "Mar", reportes: 8, resueltos: 7 },
    { mes: "Abr", reportes: 15, resueltos: 12 },
    { mes: "May", reportes: 22, resueltos: 18 },
    { mes: "Jun", reportes: 18, resueltos: 16 }
  ];

  const tiposReportesData = [
    { tipo: "Técnicos", cantidad: 45, color: "#8884d8" },
    { tipo: "Académicos", cantidad: 32, color: "#82ca9d" },
    { tipo: "Pagos", cantidad: 23, color: "#ffc658" },
    { tipo: "Otros", cantidad: 15, color: "#ff7c7c" }
  ];

  const reportesRecientes = [
    { id: 1, usuario: "Juan Pérez", tipo: "Técnico", descripcion: "Error al cargar videos", fecha: "2024-06-08", estado: "Pendiente" },
    { id: 2, usuario: "María García", tipo: "Académico", descripcion: "Calificación incorrecta", fecha: "2024-06-07", estado: "En proceso" },
    { id: 3, usuario: "Prof. López", tipo: "Técnico", descripcion: "Problemas con videollamada", fecha: "2024-06-07", estado: "Resuelto" },
    { id: 4, usuario: "Ana Torres", tipo: "Pagos", descripcion: "Error en facturación", fecha: "2024-06-06", estado: "Pendiente" }
  ];

  const estadisticasGenerales = {
    totalUsuarios: 1247,
    usuariosActivos: 892,
    reportesAbiertos: 34,
    reportesResueltos: 156
  };

  const handleViewDetails = (reporte: any) => {
    setSelectedReport(reporte);
    setShowReportDetails(true);
  };

  const handleGenerateReport = () => {
    setShowGenerateReport(true);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 p-6">
      <div className="max-w-7xl mx-auto space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Panel de Administración</h1>
            <p className="text-gray-600">Vista general del sistema educativo</p>
          </div>
          <div className="flex gap-2">
            <Button onClick={handleGenerateReport} className="bg-blue-600 hover:bg-blue-700">
              <FileText className="h-4 w-4 mr-2" />
              Generar Reporte
            </Button>
            <Link to="/login">
              <Button variant="outline">
                <LogOut className="h-4 w-4 mr-2" />
                Salir
              </Button>
            </Link>
          </div>
        </div>

        {/* Quick Actions Section */}
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <UserPlus className="h-5 w-5" />
              Gestión de Usuarios
            </CardTitle>
            <CardDescription>
              Crear nuevos usuarios en la plataforma
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Link to="/register/teacher">
                <Button className="w-full h-16 bg-green-600 hover:bg-green-700 text-lg">
                  <GraduationCap className="h-6 w-6 mr-3" />
                  Agregar Docente
                </Button>
              </Link>
              <Link to="/register/admin">
                <Button className="w-full h-16 bg-red-600 hover:bg-red-700 text-lg">
                  <Shield className="h-6 w-6 mr-3" />
                  Agregar Administrador
                </Button>
              </Link>
              <Link to="/register/student">
                <Button className="w-full h-16 bg-red-600 hover:bg-red-700 text-lg">
                  <Shield className="h-6 w-6 mr-3" />
                  Agregar Estudiante
                </Button>
              </Link>
            </div>
          </CardContent>
        </Card>

        {/* Cards de estadísticas */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Total Usuarios</CardTitle>
              <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{estadisticasGenerales.totalUsuarios}</div>
              <p className="text-xs text-muted-foreground">+12% desde el mes pasado</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Usuarios Activos</CardTitle>
              <TrendingUp className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{estadisticasGenerales.usuariosActivos}</div>
              <p className="text-xs text-muted-foreground">71% de actividad</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Reportes Abiertos</CardTitle>
              <AlertTriangle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-red-600">{estadisticasGenerales.reportesAbiertos}</div>
              <p className="text-xs text-muted-foreground">Requieren atención</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Reportes Resueltos</CardTitle>
              <BookOpen className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-green-600">{estadisticasGenerales.reportesResueltos}</div>
              <p className="text-xs text-muted-foreground">Este mes</p>
            </CardContent>
          </Card>
        </div>

        {/* Gráficas */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Gráfica de reportes por mes */}
          <Card>
            <CardHeader>
              <CardTitle>Reportes por Mes</CardTitle>
              <CardDescription>Reportes recibidos vs resueltos</CardDescription>
            </CardHeader>
            <CardContent>
              <ChartContainer
                config={{
                  reportes: { label: "Reportes", color: "#8884d8" },
                  resueltos: { label: "Resueltos", color: "#82ca9d" }
                }}
                className="h-[300px]"
              >
                <BarChart data={reportesData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="mes" />
                  <YAxis />
                  <ChartTooltip content={<ChartTooltipContent />} />
                  <Bar dataKey="reportes" fill="#8884d8" />
                  <Bar dataKey="resueltos" fill="#82ca9d" />
                </BarChart>
              </ChartContainer>
            </CardContent>
          </Card>

          {/* Gráfica de tipos de reportes */}
          <Card>
            <CardHeader>
              <CardTitle>Tipos de Reportes</CardTitle>
              <CardDescription>Distribución por categoría</CardDescription>
            </CardHeader>
            <CardContent>
              <ChartContainer
                config={{
                  tecnicos: { label: "Técnicos", color: "#8884d8" },
                  academicos: { label: "Académicos", color: "#82ca9d" },
                  pagos: { label: "Pagos", color: "#ffc658" },
                  otros: { label: "Otros", color: "#ff7c7c" }
                }}
                className="h-[300px]"
              >
                <PieChart>
                  <Pie
                    data={tiposReportesData}
                    cx="50%"
                    cy="50%"
                    outerRadius={80}
                    dataKey="cantidad"
                    label={({ tipo, cantidad }) => `${tipo}: ${cantidad}`}
                  >
                    {tiposReportesData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <ChartTooltip content={<ChartTooltipContent />} />
                </PieChart>
              </ChartContainer>
            </CardContent>
          </Card>
        </div>

        {/* Tabla de reportes recientes */}
        <Card>
          <CardHeader>
            <CardTitle>Reportes Recientes</CardTitle>
            <CardDescription>Últimos reportes registrados en el sistema</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Usuario</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Descripción</TableHead>
                  <TableHead>Fecha</TableHead>
                  <TableHead>Estado</TableHead>
                  <TableHead>Acciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {reportesRecientes.map((reporte) => (
                  <TableRow key={reporte.id}>
                    <TableCell className="font-medium">{reporte.usuario}</TableCell>
                    <TableCell>{reporte.tipo}</TableCell>
                    <TableCell>{reporte.descripcion}</TableCell>
                    <TableCell>{reporte.fecha}</TableCell>
                    <TableCell>
                      <Badge 
                        variant={
                          reporte.estado === "Resuelto" ? "default" : 
                          reporte.estado === "En proceso" ? "secondary" : "destructive"
                        }
                      >
                        {reporte.estado}
                      </Badge>
                    </TableCell>
                    <TableCell>
                      <Button 
                        variant="outline" 
                        size="sm"
                        onClick={() => handleViewDetails(reporte)}
                      >
                        Ver Detalles
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      {/* Modales */}
      {selectedReport && (
        <ReportDetailsModal
          isOpen={showReportDetails}
          onClose={() => setShowReportDetails(false)}
          report={selectedReport}
        />
      )}

      <GenerateReportModal
        isOpen={showGenerateReport}
        onClose={() => setShowGenerateReport(false)}
      />
    </div>
  );
};

export default AdminDashboard;
