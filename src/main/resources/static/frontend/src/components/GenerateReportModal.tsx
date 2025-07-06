
import { Modal } from "@/components/ui/modal"
import { Button } from "@/components/ui/button"
import { useState } from "react"

interface GenerateReportModalProps {
  isOpen: boolean
  onClose: () => void
}

export const GenerateReportModal = ({ isOpen, onClose }: GenerateReportModalProps) => {
  const [reportType, setReportType] = useState("general")
  const [dateRange, setDateRange] = useState("month")

  const handleGenerate = () => {
    console.log("Generando reporte:", { reportType, dateRange })
    // Aquí iría la lógica para generar el reporte
    onClose()
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Generar Reporte" size="md">
      <div className="space-y-4">
        <div>
          <label className="text-sm font-medium text-gray-600">Tipo de Reporte</label>
          <select 
            value={reportType} 
            onChange={(e) => setReportType(e.target.value)}
            className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
          >
            <option value="general">Reporte General</option>
            <option value="usuarios">Usuarios</option>
            <option value="reportes">Reportes por Categoría</option>
            <option value="actividad">Actividad del Sistema</option>
          </select>
        </div>

        <div>
          <label className="text-sm font-medium text-gray-600">Período</label>
          <select 
            value={dateRange} 
            onChange={(e) => setDateRange(e.target.value)}
            className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
          >
            <option value="week">Última Semana</option>
            <option value="month">Último Mes</option>
            <option value="quarter">Último Trimestre</option>
            <option value="year">Último Año</option>
          </select>
        </div>

        <div className="bg-blue-50 p-4 rounded-lg">
          <h4 className="font-medium text-blue-900">Información del Reporte</h4>
          <p className="text-sm text-blue-700 mt-1">
            El reporte incluirá estadísticas detalladas, gráficos y análisis basados en los parámetros seleccionados.
          </p>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button onClick={handleGenerate}>
            Generar Reporte
          </Button>
        </div>
      </div>
    </Modal>
  )
}
