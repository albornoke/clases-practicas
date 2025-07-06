
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Menu, X, BookOpen, User, LogIn } from "lucide-react";
import { Link } from "react-router-dom";

export const Navigation = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="bg-white/80 backdrop-blur-md shadow-sm sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <div className="flex items-center space-x-2">
            <BookOpen className="h-8 w-8 text-primary" />
            <span className="text-xl font-bold text-primary">EduPlatform</span>
          </div>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center space-x-8">
            <a href="#inicio" className="text-gray-700 hover:text-primary transition-colors">
              Inicio
            </a>
            <a href="#servicios" className="text-gray-700 hover:text-primary transition-colors">
              Servicios
            </a>
            <a href="#sobre-mi" className="text-gray-700 hover:text-primary transition-colors">
              Sobre Mí
            </a>
            <a href="#contacto" className="text-gray-700 hover:text-primary transition-colors">
              Contacto
            </a>
            <Link to="/login">
              <Button variant="outline" size="sm" className="flex items-center space-x-1">
                <LogIn className="h-4 w-4" />
                <span>Acceder</span>
              </Button>
            </Link>
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden">
            <Button
              variant="ghost"
              size="sm"
              onClick={() => setIsOpen(!isOpen)}
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </Button>
          </div>
        </div>

        {/* Mobile Menu */}
        {isOpen && (
          <div className="md:hidden bg-white border-t border-gray-200">
            <div className="px-2 pt-2 pb-3 space-y-1">
              <a href="#inicio" className="block px-3 py-2 text-gray-700 hover:text-primary">
                Inicio
              </a>
              <a href="#servicios" className="block px-3 py-2 text-gray-700 hover:text-primary">
                Servicios
              </a>
              <a href="#sobre-mi" className="block px-3 py-2 text-gray-700 hover:text-primary">
                Sobre Mí
              </a>
              <a href="#contacto" className="block px-3 py-2 text-gray-700 hover:text-primary">
                Contacto
              </a>
              <Link to="/login" className="block px-3 py-2">
                <Button variant="outline" size="sm" className="w-full">
                  Acceder
                </Button>
              </Link>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};
