
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { BookOpen, Video, Users, FileText, Calendar, Award } from "lucide-react";

export const Services = () => {
  const services = [
    {
      icon: Video,
      title: "Clases Virtuales",
      description: "Sesiones en vivo con pizarra digital interactiva y grabaciones disponibles",
      features: ["Horarios flexibles", "Clases grabadas", "Materiales digitales"]
    },
    {
      icon: Users,
      title: "Clases Presenciales",
      description: "Atención personalizada en un ambiente cómodo y familiar",
      features: ["Atención 1:1", "Materiales incluidos", "Seguimiento personalizado"]
    },
    {
      icon: FileText,
      title: "Recursos Educativos",
      description: "Guías, ejercicios y material didáctico adaptado a cada nivel",
      features: ["Guías PDF", "Ejercicios interactivos", "Evaluaciones"]
    },
    {
      icon: Calendar,
      title: "Planificación",
      description: "Agenda y seguimiento del progreso académico de cada estudiante",
      features: ["Calendario integrado", "Reportes de progreso", "Comunicación padres"]
    },
    {
      icon: BookOpen,
      title: "Materias Principales",
      description: "Cobertura completa de matemáticas, español, ciencias y más",
      features: ["Primaria completa", "Secundaria básica", "Preparación exámenes"]
    },
    {
      icon: Award,
      title: "Certificaciones",
      description: "Reconocimientos y certificados de completación de cursos",
      features: ["Certificados digitales", "Badges de logros", "Portfolio estudiantil"]
    }
  ];

  return (
    <section id="servicios" className="py-20 bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center space-y-4 mb-16">
          <h2 className="text-3xl lg:text-4xl font-bold text-gray-900">
            Servicios Educativos
          </h2>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Una plataforma completa diseñada para el éxito académico de tus hijos
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {services.map((service, index) => (
            <Card key={index} className="group hover:shadow-lg transition-shadow duration-300 border-0 shadow-sm bg-white">
              <CardHeader className="text-center">
                <div className="mx-auto w-16 h-16 bg-gradient-to-br from-blue-500 to-purple-500 rounded-2xl flex items-center justify-center mb-4 group-hover:scale-110 transition-transform duration-300">
                  <service.icon className="h-8 w-8 text-white" />
                </div>
                <CardTitle className="text-xl font-bold">{service.title}</CardTitle>
                <CardDescription className="text-gray-600">
                  {service.description}
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <ul className="space-y-2">
                  {service.features.map((feature, featureIndex) => (
                    <li key={featureIndex} className="flex items-center text-sm text-muted-foreground">
                      <div className="w-2 h-2 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full mr-3"></div>
                      {feature}
                    </li>
                  ))}
                </ul>
                <Button variant="outline" className="w-full group-hover:bg-primary group-hover:text-primary-foreground transition-colors">
                  Más Información
                </Button>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
};
