
import { Modal } from "@/components/ui/modal"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { useState } from "react"

interface ReportDetailsModalProps {
  isOpen: boolean
  onClose: () => void
  report: {
    id: number
    usuario: string
    tipo: string
    descripcion: string
    fecha: string
    estado: string
  }
}

export const ReportDetailsModal = ({ isOpen, onClose, report }: ReportDetailsModalProps) => {
  const [resolution, setResolution] = useState("")
  const [newStatus, setNewStatus] = useState(report.estado)

  const handleResolve = () => {
    console.log("Resolviendo reporte:", { reportId: report.id, resolution, newStatus })
    // Aquí iría la lógica para resolver el reporte
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Detalles del Reporte" size="lg">
      <div className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="text-sm font-medium text-muted-foreground">Usuario</label>
            <p className="text-lg">{report.usuario}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Tipo</label>
            <p className="text-lg">{report.tipo}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Fecha</label>
            <p className="text-lg">{report.fecha}</p>
          </div>
          <div>
            <label className="text-sm font-medium text-muted-foreground">Estado Actual</label>
            <Badge 
              variant={
                report.estado === "Resuelto" ? "default" : 
                report.estado === "En proceso" ? "secondary" : "destructive"
              }
            >
              {report.estado}
            </Badge>
          </div>
        </div>
        
        <div>
          <label className="text-sm font-medium text-muted-foreground">Descripción</label>
          <p className="text-lg mt-1 p-3 bg-muted/50 rounded border">{report.descripcion}</p>
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Resolución</label>
          <Textarea
            value={resolution}
            onChange={(e) => setResolution(e.target.value)}
            placeholder="Describe la resolución del reporte..."
            className="mt-1"
            rows={4}
          />
        </div>

        <div>
          <label className="text-sm font-medium text-muted-foreground">Nuevo Estado</label>
          <select 
            value={newStatus} 
            onChange={(e) => setNewStatus(e.target.value)}
            className="mt-1 block w-full rounded-md border border-input bg-background px-3 py-2 text-foreground"
          >
            <option value="Pendiente">Pendiente</option>
            <option value="En proceso">En proceso</option>
            <option value="Resuelto">Resuelto</option>
          </select>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button onClick={handleResolve}>
            Actualizar Reporte
          </Button>
        </div>
      </div>
    </Modal>
  )
}
