
import { Modal } from "@/components/ui/modal"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { Badge } from "@/components/ui/badge"
import { useState } from "react"

interface ReviewAssignmentModalProps {
  isOpen: boolean
  onClose: () => void
  assignment: {
    id: number
    estudiante: string
    actividad: string
    materia: string
    fechaEntrega: string
    tipo: string
  }
}

export const ReviewAssignmentModal = ({ isOpen, onClose, assignment }: ReviewAssignmentModalProps) => {
  const [feedback, setFeedback] = useState("")

  const handleReview = () => {
    console.log("Revisando actividad:", { assignmentId: assignment.id, feedback })
    // Aquí iría la lógica para guardar la revisión
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Revisar Actividad" size="lg">
      <div className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="text-sm font-medium text-muted-foreground">Estudiante</label>
            <p className="text-lg">{assignment.estudiante}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Actividad</label>
            <p className="text-lg">{assignment.actividad}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Materia</label>
            <p className="text-lg">{assignment.materia}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Tipo</label>
            <Badge variant="outline">{assignment.tipo}</Badge>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Fecha de Entrega</label>
          <p className="text-lg">{assignment.fechaEntrega}</p>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Contenido Entregado</label>
          <div className="mt-1 p-4 bg-muted/50 rounded-lg border">
            <p>Aquí se mostraría el contenido que el estudiante entregó para esta actividad. Podría incluir texto, archivos adjuntos, etc.</p>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Comentarios de Revisión</label>
          <Textarea
            value={feedback}
            onChange={(e) => setFeedback(e.target.value)}
            placeholder="Escribe tus comentarios sobre la actividad del estudiante..."
            className="mt-1"
            rows={4}
          />
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button onClick={handleReview}>
            Guardar Revisión
          </Button>
        </div>
      </div>
    </Modal>
  )
}
