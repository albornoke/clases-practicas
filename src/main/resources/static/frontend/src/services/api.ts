import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    withCredentials: true, // <-- necesario si usas cookies
    headers: {
      'Content-Type': 'application/json',
    },
});      

// Interceptor para añadir el token JWT a las solicitudes
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;

export async function fetchOnlineStudents() {
  const response = await fetch("/api/estudiante/online");
  if (!response.ok) throw new Error("Error al obtener estudiantes online");
  return response.json();
}

