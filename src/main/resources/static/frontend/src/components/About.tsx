
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { GraduationCap, Heart, Trophy, Users } from "lucide-react";

export const About = () => {
  const achievements = [
    { icon: GraduationCap, label: "Licenciada en Educación", value: "Universidad Nacional" },
    { icon: Users, label: "Estudiantes Graduados", value: "100+" },
    { icon: Trophy, label: "Años de Experiencia", value: "25+" },
    { icon: Heart, label: "Pasión por Enseñar", value: "Desde siempre" }
  ];

  const specialties = [
    "Matemáticas Básicas",
    "Español y Literatura", 
    "Ciencias Naturales",
    "Preparación de Exámenes",
    "Técnicas de Estudio",
    "Apoyo Emocional"
  ];

  return (
    <section id="sobre-mi" className="py-20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid lg:grid-cols-2 gap-16 items-center">
          {/* Content */}
          <div className="space-y-8">
            <div className="space-y-4">
              <h2 className="text-3xl lg:text-4xl font-bold text-gray-900">
                Conoce a tu
                <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">
                  {" "}Profesora
                </span>
              </h2>
              
              <p className="text-lg text-gray-600 leading-relaxed">
                Soy María, una educadora apasionada con más de 25 años de experiencia 
                ayudando a niños y adolescentes a alcanzar su máximo potencial académico. 
                Mi enfoque personalizado y metodología comprobada han transformado la 
                experiencia de aprendizaje de más de 100 estudiantes.
              </p>
              
              <p className="text-lg text-gray-600 leading-relaxed">
                Creo firmemente que cada estudiante es único y merece una atención 
                personalizada que respete su ritmo de aprendizaje y potencie sus fortalezas.
              </p>
            </div>

            {/* Specialties */}
            <div className="space-y-4">
              <h3 className="text-xl font-semibold text-gray-900">Especialidades:</h3>
              <div className="flex flex-wrap gap-2">
                {specialties.map((specialty, index) => (
                  <Badge key={index} variant="secondary" className="bg-blue-50 text-blue-700 hover:bg-blue-100">
                    {specialty}
                  </Badge>
                ))}
              </div>
            </div>

            <Button size="lg" className="bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700">
              Agenda una Consulta Gratuita
            </Button>
          </div>

          {/* Achievements */}
          <div className="space-y-6">
            <div className="bg-gradient-to-br from-blue-50 to-purple-50 rounded-3xl p-8">
              <h3 className="text-2xl font-bold text-gray-900 mb-6 text-center">
                Mi Trayectoria
              </h3>
              
              <div className="grid grid-cols-2 gap-6">
                {achievements.map((achievement, index) => (
                  <div key={index} className="text-center space-y-3">
                    <div className="mx-auto w-16 h-16 bg-white rounded-2xl flex items-center justify-center shadow-sm">
                      <achievement.icon className="h-8 w-8 text-blue-600" />
                    </div>
                    <div>
                      <div className="font-bold text-lg text-gray-900">{achievement.value}</div>
                      <div className="text-sm text-gray-600">{achievement.label}</div>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Testimonial */}
            <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100">
              <div className="flex items-start space-x-4">
                <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center flex-shrink-0">
                  <span className="text-white font-bold">A</span>
                </div>
                <div className="space-y-2">
                  <p className="text-gray-700 italic">
                    "María transformó completamente la relación de mi hijo con las matemáticas. 
                    Su paciencia y metodología son excepcionales."
                  </p>
                  <div className="text-sm text-gray-600">
                    <strong>Ana Rodríguez</strong> - Madre de estudiante
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
