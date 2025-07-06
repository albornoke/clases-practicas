
import apiClient from './api';
import { User } from './authService';

export const userService = {
  async getAllUsers(): Promise<User[]> {
    const response = await apiClient.get('/users');
    return response.data;
  },

  async getUserById(id: string): Promise<User> {
    const response = await apiClient.get(`/users/${id}`);
    return response.data;
  },

  async updateUser(id: string, data: Partial<User>): Promise<User> {
    const response = await apiClient.put(`/users/${id}`, data);
    return response.data;
  },

  async deleteUser(id: string): Promise<void> {
    await apiClient.delete(`/users/${id}`);
  },

  async getStudents(): Promise<User[]> {
    const response = await apiClient.get('/api/usuarios/rol/ESTUDIANTE');
    return response.data;
  },

  async getTeachers(): Promise<User[]> {
    const response = await apiClient.get('/api/usuarios/rol/DOCENTE');
    return response.data;
  },

  async getAdmins(): Promise<User[]> {
    const response = await apiClient.get('/api/usuarios/rol/ADMIN');
    return response.data;
  },
};
