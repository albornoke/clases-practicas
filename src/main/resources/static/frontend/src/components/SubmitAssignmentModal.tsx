
import { Modal } from "@/components/ui/modal"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { useState } from "react"
import { Upload } from "lucide-react"

interface SubmitAssignmentModalProps {
  isOpen: boolean
  onClose: () => void
  assignment: {
    id: number
    titulo: string
    materia: string
    fechaVencimiento: string
    tipo: string
  }
}

export const SubmitAssignmentModal = ({ isOpen, onClose, assignment }: SubmitAssignmentModalProps) => {
  const [content, setContent] = useState("")
  const [file, setFile] = useState<File | null>(null)

  const handleSubmit = () => {
    console.log("Entregando actividad:", { assignmentId: assignment.id, content, file })
    // Aquí iría la lógica para entregar la actividad
    onClose()
  }

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setFile(e.target.files[0])
    }
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Entregar Actividad" size="lg">
      <div className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="text-sm font-medium text-gray-600">Actividad</label>
            <p className="text-lg">{assignment.titulo}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Materia</label>
            <p className="text-lg">{assignment.materia}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Tipo</label>
            <p className="text-lg">{assignment.tipo}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-gray-600">Fecha Límite</label>
            <p className="text-lg">{assignment.fechaVencimiento}</p>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-gray-600">Contenido de la Entrega</label>
          <Textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            placeholder="Escribe tu respuesta o descripción de la actividad..."
            className="mt-1"
            rows={6}
          />
        </div>

        <div>
          <label className="text-sm font-medium text-gray-600">Archivo Adjunto (Opcional)</label>
          <div className="mt-1 flex items-center space-x-2">
            <input
              type="file"
              onChange={handleFileChange}
              className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
            />
            <Upload className="h-4 w-4 text-gray-400" />
          </div>
          {file && (
            <p className="text-sm text-green-600 mt-1">Archivo seleccionado: {file.name}</p>
          )}
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button onClick={handleSubmit}>
            Entregar Actividad
          </Button>
        </div>
      </div>
    </Modal>
  )
}
