
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { MapPin, Phone, Mail, Clock, MessageCircle } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import apiClient from "../services/api"; // Import the apiClient

export const Contact = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    correo: "",
    mensaje: "",
    telefono: "",
    asunto: ""    
  });
  const { toast } = useToast();

  const handleSubmit = async (e: React.FormEvent) => { 
    e.preventDefault();
    const dataToSend = { ...formData };
    try {      
      await apiClient.post('/api/contacto');
      toast({
        title: "Mensaje enviado",
        description: "Te contactaré pronto para coordinar una consulta.",
      });
      setFormData({ nombre: "", correo: "", telefono: "", mensaje: "", asunto: "" });
    } catch (error) {
      console.error("Error sending message:", error);
      toast({
        title: "Error",
        description: "Hubo un problema al enviar tu mensaje. Por favor, inténtalo de nuevo.",
        variant: "destructive",
      });
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const contactInfo = [
    {
      icon: Phone,
      title: "Teléfono",
      value: "+57 312 762 6430",
      description: "Lun - Vie: 9:00 AM - 6:00 PM"
    },
    {
      icon: Mail,
      title: "Email",
      value: "marialaprofe-2009@hotmail.com",
      description: "Respuesta en 24 horas"
    },
    {
      icon: MapPin,
      title: "Ubicación",
      value: "Cali, Colombia",
      description: "Clases presenciales disponibles"
    },
    {
      icon: Clock,
      title: "Horarios",
      value: "Flexible",
      description: "Adaptado a tu disponibilidad"
    }
  ];

  return (
    <section id="contacto" className="py-20 bg-muted/50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center space-y-4 mb-16">
          <h2 className="text-3xl lg:text-4xl font-bold text-foreground">
            ¿Listo para Comenzar?
          </h2>
          <p className="text-xl text-muted-foreground max-w-3xl mx-auto">
            Contáctame para una consulta gratuita y descubre cómo puedo ayudar en el crecimiento académico
          </p>
        </div>

        <div className="grid lg:grid-cols-3 gap-12">
          {/* Contact Info */}
          <div className="space-y-6">
            <div className="space-y-4">
              <h3 className="text-2xl font-bold text-foreground">Información de Contacto</h3>
              <p className="text-muted-foreground">
                Estoy aquí para responder todas tus preguntas y ayudarte a encontrar la mejor solución educativa.
              </p>
            </div>

            <div className="space-y-4">
              {contactInfo.map((info, index) => (
                <div key={index} className="flex items-start space-x-4">
                  <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-purple-500 rounded-lg flex items-center justify-center flex-shrink-0">
                    <info.icon className="h-6 w-6 text-white" />
                  </div>
                  <div>
                    <h4 className="font-semibold text-foreground">{info.title}</h4>
                    <p className="text-muted-foreground">{info.value}</p>
                    <p className="text-sm text-muted-foreground/70">{info.description}</p>
                  </div>
                </div>
              ))}
            </div>

            <div className="bg-muted/30 rounded-2xl p-6 border">
              <div className="flex items-center space-x-3 mb-4">
                <MessageCircle className="h-6 w-6 text-primary" />
                <h4 className="font-semibold text-foreground">Consulta Gratuita</h4>
              </div>
              <p className="text-muted-foreground text-sm">
                Agenda una sesión de 30 minutos sin costo para conocer mis métodos y evaluar las necesidades específicas.
              </p>
            </div>
          </div>

          {/* Contact Form */}
          <div className="lg:col-span-2">
            <Card className="border shadow-lg">
              <CardHeader>
                <CardTitle className="text-2xl font-bold text-foreground">
                  Envíame un Mensaje
                </CardTitle>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="space-y-6">
                  <div className="grid md:grid-cols-2 gap-6">
                    <div className="space-y-2">
                      <label htmlFor="name" className="text-sm font-medium text-foreground">
                        Nombre Completo *
                      </label>
                      <Input
                        id="name"
                        name="nombre"
                        value={formData.nombre}
                        onChange={handleChange}
                        placeholder="Tu nombre"
                        required
                      />
                    </div>
                    <div className="space-y-2">
                      <label htmlFor="email" className="text-sm font-medium text-foreground">
                        Email *
                      </label>
                      <Input
                        id="email"
                        name="correo"
                        type="email"
                        value={formData.correo}
                        onChange={handleChange}
                        placeholder="tu@email.com"
                        required
                      />
                    </div>
                  </div>

                  <div className="grid md:grid-cols-2 gap-6">
                    <div className="space-y-2">
                      <label htmlFor="phone" className="text-sm font-medium text-foreground">
                        Teléfono
                      </label>
                      <Input
                        id="phone"
                        name="telefono"
                        value={formData.telefono}
                        onChange={handleChange}
                        placeholder="+1 (555) 123-4567"
                      />
                    </div>
                    <div className="space-y-2">
                      <label htmlFor="subject" className="text-sm font-medium text-foreground">
                        Asunto *
                      </label>
                      <Input
                        id="subject"
                        name="asunto"
                        value={formData.asunto}
                        onChange={handleChange}
                        placeholder="Asunto de tu consulta"                  
                      />                      
                    </div>
                  </div>

                  <div className="space-y-2">
                    <label htmlFor="message" className="text-sm font-medium text-foreground">
                      Mensaje *
                    </label>
                    <Textarea
                      id="message"
                      name="mensaje"
                      value={formData.mensaje}
                      onChange={handleChange}
                      placeholder="Cuéntame sobre las necesidades específicas, edad del estudiante, materias de interés, etc."
                      className="min-h-[120px]"
                      required
                    />
                  </div>

                  <Button 
                    type="submit" 
                    size="lg" 
                    className="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700"
                  >
                    Enviar Mensaje
                  </Button>
                </form>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};
