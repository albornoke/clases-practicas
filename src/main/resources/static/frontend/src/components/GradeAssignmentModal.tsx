
import { Modal } from "@/components/ui/modal"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { Badge } from "@/components/ui/badge"
import { useState } from "react"

interface GradeAssignmentModalProps {
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

export const GradeAssignmentModal = ({ isOpen, onClose, assignment }: GradeAssignmentModalProps) => {
  const [grade, setGrade] = useState("")
  const [feedback, setFeedback] = useState("")

  const handleGrade = () => {
    const numericGrade = parseFloat(grade)
    if (numericGrade < 0 || numericGrade > 5) {
      alert("La calificación debe estar entre 0.0 y 5.0")
      return
    }
    
    console.log("Calificando actividad:", { assignmentId: assignment.id, grade: numericGrade, feedback })
    // Aquí iría la lógica para guardar la calificación
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Calificar Actividad" size="lg">
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
            <p>Aquí se mostraría el contenido que el estudiante entregó para esta actividad.</p>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Calificación (0.0 - 5.0)</label>
          <input
            type="number"
            min="0"
            max="5"
            step="0.1"
            value={grade}
            onChange={(e) => setGrade(e.target.value)}
            placeholder="Ej: 4.5"
            className="mt-1 block w-full rounded-md border border-input bg-background px-3 py-2 text-foreground"
          />
          <p className="text-xs text-muted-foreground mt-1">Ingresa una calificación entre 0.0 y 5.0</p>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Comentarios</label>
          <Textarea
            value={feedback}
            onChange={(e) => setFeedback(e.target.value)}
            placeholder="Escribe comentarios sobre el desempeño del estudiante..."
            className="mt-1"
            rows={4}
          />
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button onClick={handleGrade}>
            Guardar Calificación
          </Button>
        </div>
      </div>
    </Modal>
  )
}
