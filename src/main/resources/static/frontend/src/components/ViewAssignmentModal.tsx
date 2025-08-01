
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
            <label className="text-sm font-medium text-muted-foreground">Título</label>
            <p className="text-lg">{assignment.titulo}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Materia</label>
            <p className="text-lg">{assignment.materia}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Tipo</label>
            <Badge variant="outline">{assignment.tipo}</Badge>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Prioridad</label>
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
          <label className="text-sm font-medium text-muted-foreground">Fecha de Vencimiento</label>
          <p className="text-lg">{assignment.fechaVencimiento}</p>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Descripción</label>
          <div className="mt-1 p-4 bg-muted/50 rounded-lg border">
            <p>Esta es una actividad importante que requiere tu atención. Asegúrate de completarla antes de la fecha límite.</p>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Instrucciones</label>
          <div className="mt-1 p-4 bg-primary/5 rounded-lg border border-primary/20">
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
