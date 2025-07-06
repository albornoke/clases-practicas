
import { Modal } from "@/components/ui/modal"
import { Badge } from "@/components/ui/badge"

interface ViewAssignmentModalProps {
  isOpen: boolean
  onClose: () => void
  assignment: {
    id: number
    titulo: string
    materia: string
    fechaVencimiento: string
    tipo: string
    prioridad: string
  }
}

export const ViewAssignmentModal = ({ isOpen, onClose, assignment }: ViewAssignmentModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Detalles de la Actividad" size="lg">
      <div className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="text-sm font-medium text-gray-600">Título</label>
            <p className="text-lg">{assignment.titulo}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Materia</label>
            <p className="text-lg">{assignment.materia}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Tipo</label>
            <Badge variant="outline">{assignment.tipo}</Badge>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Prioridad</label>
            <Badge 
              variant={
                assignment.prioridad === "Alta" ? "destructive" : 
                assignment.prioridad === "Media" ? "default" : "secondary"
              }
            >
              {assignment.prioridad}
            </Badge>
          </div>
        </div>
        
        <div>
          <label className="text-sm font-medium text-gray-600">Fecha de Vencimiento</label>
          <p className="text-lg">{assignment.fechaVencimiento}</p>
        </div>

        <div>
          <label className="text-sm font-medium text-gray-600">Descripción</label>
          <div className="mt-1 p-4 bg-gray-50 rounded-lg">
            <p>Esta es una actividad importante que requiere tu atención. Asegúrate de completarla antes de la fecha límite.</p>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-gray-600">Instrucciones</label>
          <div className="mt-1 p-4 bg-blue-50 rounded-lg">
            <ul className="list-disc list-inside space-y-1 text-sm">
              <li>Lee cuidadosamente los materiales proporcionados</li>
              <li>Completa todas las secciones requeridas</li>
              <li>Revisa tu trabajo antes de entregarlo</li>
              <li>Contacta al profesor si tienes dudas</li>
            </ul>
          </div>
        </div>
      </div>
    </Modal>
  )
}
