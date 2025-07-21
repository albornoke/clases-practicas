
import apiClient from './api';

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterData {
  name: string;
  lastName: string;
  email: string;
  password: string;
  phone?: string;
  rol: 'ESTUDIANTE' | 'DOCENTE' | 'ADMIN';
  grade?: string;
  description?: string;
  profileImage?: File;
}

export interface User {
  id: string;
  name: string;
  lastName: string;
  email: string;
  rol: 'ESTUDIANTE' | 'DOCENTE' | 'ADMIN';
  phone?: string;
  grade?: string;
  description?: string;
  profileImage?: string;
}

export const authService = {
  async login(credentials: LoginCredentials): Promise<{ user: User; token: string }> {
    const response = await apiClient.post('/api/usuarios/login', credentials);
    const { user, token } = response.data;
    localStorage.setItem('authToken', token);
    return { user, token };
  },

  async register(data: RegisterData): Promise<{ user: User; token: string }> {
    const formData = new FormData();
    Object.entries(data).forEach(([key, value]) => {
      if (value !== undefined) {
        formData.append(key, value);
      }
    });

    const response = await apiClient.post('/api/usuarios/registro', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    
    const { user, token } = response.data;
    localStorage.setItem('authToken', token);
    return { user, token };
  },

  async getCurrentUser(): Promise<User> {
    const response = await apiClient.get('/api/usuarios/perfil');
    return response.data;
  },

  logout(): void {
    localStorage.removeItem('authToken');
  },

  isAuthenticated(): boolean {
    return !!localStorage.getItem('authToken');
  },
};
