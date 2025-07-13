
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { BookOpen, Upload, ArrowLeft } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";

const StudentRegister = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    apellido: "",
    correo: "",
    contraseña: "",
    telefono: "",
    descripcion: "",
    grado: "",
    foto: null as File | null
  });
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] || null;
    setFormData(prev => ({ ...prev, foto: file }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const form = new FormData();
      form.append("nombre", formData.nombre);
      form.append("apellido", formData.apellido);
      form.append("correo", formData.correo);
      form.append("contraseña", formData.contraseña);
      form.append("telefono", formData.telefono);
      form.append("descripcion", formData.descripcion);
      form.append("grado", formData.grado);
      if (formData.foto) {
        form.append("foto", formData.foto);
      }
      const response = await fetch("/api/estudiante/registro", {
        method: "POST",
        body: form
      });
      if(response.ok){
        toast({
          title: "Estudiante registrado",
          description: "La cuenta del estudiante ha sido creada correctamente",
        });
        navigate("/admin");
      }else{
        const error = await response.text();
        toast({
          title: "Error al registrar al estudiante",
          description: error,
          variant: "destructive"
        });
      }
    }catch(err){
      toast({
        title: "Error de red",
        description: "No se pudo conectar con el servidor",
        variant: "destructive"
      });
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        {/* Header */}
        <div className="text-center mb-8">
          <Link to="/" className="inline-flex items-center space-x-2 mb-6">
            <BookOpen className="h-8 w-8 text-primary" />
            <span className="text-2xl font-bold text-primary">EduPlatform</span>
          </Link>
          <h1 className="text-3xl font-bold text-gray-900">Registro de Estudiante</h1>
          <p className="text-gray-600 mt-2">Crea tu cuenta para comenzar a aprender</p>
        </div>
        {/* Registration Form */}
        <Card className="shadow-xl border-0">
          <CardHeader className="text-center pb-4">
            <CardTitle className="text-xl">Información Personal</CardTitle>
            <CardDescription>
              Completa todos los campos para crear tu cuenta
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="nombre">Nombre</Label>
                  <Input
                    id="nombre"
                    type="text"
                    placeholder="Tu nombre"
                    value={formData.nombre}
                    onChange={(e) => handleInputChange("nombre", e.target.value)}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="apellido">Apellido</Label>
                  <Input
                    id="apellido"
                    type="text"
                    placeholder="Tu apellido"
                    value={formData.apellido}
                    onChange={(e) => handleInputChange("apellido", e.target.value)}
                    required
                  />
                </div>
              </div>
              <div className="space-y-2">
                <Label htmlFor="correo">Correo Electrónico</Label>
                <Input
                  id="correo"
                  type="email"
                  placeholder="estudiante@email.com"
                  value={formData.correo}
                  onChange={(e) => handleInputChange("correo", e.target.value)}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="contraseña">Contraseña</Label>
                <Input
                  id="contraseña"
                  type="password"
                  placeholder="••••••••"
                  value={formData.contraseña}
                  onChange={(e) => handleInputChange("contraseña", e.target.value)}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="telefono">Teléfono</Label>
                <Input
                  id="telefono"
                  type="tel"
                  placeholder="+57 300 123 4567"
                  value={formData.telefono}
                  onChange={(e) => handleInputChange("telefono", e.target.value)}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="grado">Grado</Label>
                <Select value={formData.grado} onValueChange={(value) => handleInputChange("grado", value)} required>
                  <SelectTrigger>
                    <SelectValue placeholder="Selecciona tu grado" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="1">1° Primaria</SelectItem>
                    <SelectItem value="2">2° Primaria</SelectItem>
                    <SelectItem value="3">3° Primaria</SelectItem>
                    <SelectItem value="4">4° Primaria</SelectItem>
                    <SelectItem value="5">5° Primaria</SelectItem>
                    <SelectItem value="6">6° Bachillerato</SelectItem>
                    <SelectItem value="7">7° Bachillerato</SelectItem>
                    <SelectItem value="8">8° Bachillerato</SelectItem>
                    <SelectItem value="9">9° Bachillerato</SelectItem>
                    <SelectItem value="10">10° Bachillerato</SelectItem>
                    <SelectItem value="11">11° Bachillerato</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="space-y-2">
                <Label htmlFor="foto">Foto de Perfil</Label>
                <div className="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
                  <input
                    id="foto"
                    type="file"
                    accept="image/*"
                    onChange={handleFileChange}
                    className="hidden"
                  />
                  <label
                    htmlFor="foto"
                    className="cursor-pointer flex flex-col items-center space-y-2"
                  >
                    <Upload className="h-8 w-8 text-gray-400" />
                    <span className="text-sm text-gray-600">
                      {formData.foto ? formData.foto.name : "Seleccionar imagen"}
                    </span>
                  </label>
                </div>
              </div>
              <Button type="submit" className="w-full bg-blue-600 hover:bg-blue-700">
                Crear Cuenta
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default StudentRegister;
