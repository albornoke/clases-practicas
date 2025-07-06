
import { BookOpen, Facebook, Instagram, Linkedin, Twitter } from "lucide-react";

export const Footer = () => {
  return (
    <footer className="bg-gray-900 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid md:grid-cols-4 gap-8">
          {/* Brand */}
          <div className="space-y-4">
            <div className="flex items-center space-x-2">
              <BookOpen className="h-8 w-8 text-blue-400" />
              <span className="text-xl font-bold">EduPlatform</span>
            </div>
            <p className="text-gray-400">
              Transformando el futuro académico de los niños con educación personalizada y de calidad.
            </p>
            <div className="flex space-x-4">
              <Facebook className="h-5 w-5 text-gray-400 hover:text-blue-400 cursor-pointer transition-colors" />
              <Instagram className="h-5 w-5 text-gray-400 hover:text-blue-400 cursor-pointer transition-colors" />
              <Twitter className="h-5 w-5 text-gray-400 hover:text-blue-400 cursor-pointer transition-colors" />
              <Linkedin className="h-5 w-5 text-gray-400 hover:text-blue-400 cursor-pointer transition-colors" />
            </div>
          </div>

          {/* Services */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold">Servicios</h3>
            <ul className="space-y-2 text-gray-400">
              <li className="hover:text-white cursor-pointer transition-colors">Clases Virtuales</li>
              <li className="hover:text-white cursor-pointer transition-colors">Clases Presenciales</li>
              <li className="hover:text-white cursor-pointer transition-colors">Recursos Educativos</li>
              <li className="hover:text-white cursor-pointer transition-colors">Evaluaciones</li>
            </ul>
          </div>

          {/* Subjects */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold">Materias</h3>
            <ul className="space-y-2 text-gray-400">
              <li className="hover:text-white cursor-pointer transition-colors">Matemáticas</li>
              <li className="hover:text-white cursor-pointer transition-colors">Español</li>
              <li className="hover:text-white cursor-pointer transition-colors">Ciencias</li>
              <li className="hover:text-white cursor-pointer transition-colors">Historia</li>
            </ul>
          </div>

          {/* Contact */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold">Contacto</h3>
            <ul className="space-y-2 text-gray-400">
              <li>+57 312 762 6430</li>
              <li>marialaprofe-2009@hotmail.com</li>
              <li>Cali, Colombia</li>
              <li>Lun - Vie: 9:00 AM - 6:00 PM</li>
            </ul>
          </div>
        </div>

        <div className="border-t border-gray-800 mt-12 pt-8 text-center text-gray-400">
          <p>&copy; 2024 EduPlatform. Todos los derechos reservados.</p>
        </div>
      </div>
    </footer>
  );
};
