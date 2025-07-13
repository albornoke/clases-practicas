import { Moon, Sun } from "lucide-react"
import { useTheme } from "@/components/theme-provider"

export function ThemeToggle() {
  const { theme, setTheme } = useTheme()

  return (
    <button
      onClick={() => setTheme(theme === "light" ? "dark" : "light")}
      className="relative flex items-center justify-center w-12 h-12 rounded-full shadow-lg transition-all duration-300 bg-gradient-to-tr from-yellow-400 via-white to-blue-400 dark:from-blue-900 dark:via-gray-900 dark:to-purple-900 hover:scale-110 border-2 border-primary/30"
      aria-label="Cambiar tema"
    >
      <Sun className="absolute h-7 w-7 text-yellow-500 transition-all duration-300 opacity-100 scale-100 rotate-0 dark:opacity-0 dark:scale-0 dark:-rotate-90" />
      <Moon className="absolute h-7 w-7 text-blue-400 transition-all duration-300 opacity-0 scale-0 rotate-90 dark:opacity-100 dark:scale-100 dark:rotate-0" />
    </button>
  )
}