
import { authService } from '@/services/authService';
import apiClient from '@/services/api';

// Mock del cliente API
jest.mock('@/services/api');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

// Mock de localStorage
const localStorageMock = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
};
Object.defineProperty(window, 'localStorage', {
  value: localStorageMock,
});

describe('AuthService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('login', () => {
    it('should login successfully and store token', async () => {
      const mockResponse = {
        data: {
          user: {
            id: '1',
            name: 'Juan',
            lastName: 'Pérez',
            email: 'juan@test.com',
            role: 'estudiante',
          },
          token: 'mock-token',
        },
      };

      mockedApiClient.post.mockResolvedValue(mockResponse);

      const result = await authService.login({
        email: 'juan@test.com',
        password: 'password123',
      });

      expect(mockedApiClient.post).toHaveBeenCalledWith('/auth/login', {
        email: 'juan@test.com',
        password: 'password123',
      });
      expect(localStorageMock.setItem).toHaveBeenCalledWith('authToken', 'mock-token');
      expect(result.user.email).toBe('juan@test.com');
      expect(result.token).toBe('mock-token');
    });

    it('should throw error on failed login', async () => {
      mockedApiClient.post.mockRejectedValue(new Error('Invalid credentials'));

      await expect(
        authService.login({ email: 'wrong@test.com', password: 'wrong' })
      ).rejects.toThrow('Invalid credentials');
    });
  });

  describe('register', () => {
    it('should register user successfully', async () => {
      const mockResponse = {
        data: {
          user: {
            id: '2',
            name: 'María',
            lastName: 'Albornoz',
            email: 'maria@test.com',
            role: 'docente', 
          },
          token: 'new-token',
        },
      };

      mockedApiClient.post.mockResolvedValue(mockResponse);

      const registerData = {
        name: 'María',
        lastName: 'González',
        email: 'maria@test.com',
        password: 'password123',
        role: 'docente' as const,
        description: 'Profesora de matemáticas',
      };

      const result = await authService.register(registerData);

      expect(mockedApiClient.post).toHaveBeenCalledWith(
        '/auth/register',
        expect.any(FormData),
        { headers: { 'Content-Type': 'multipart/form-data' } }
      );
      expect(result.user.name).toBe('María');
    });
  });

  describe('logout', () => {
    it('should remove token from localStorage', () => {
      authService.logout();
      expect(localStorageMock.removeItem).toHaveBeenCalledWith('authToken');
    });
  });

  describe('isAuthenticated', () => {
    it('should return true when token exists', () => {
      localStorageMock.getItem.mockReturnValue('mock-token');
      expect(authService.isAuthenticated()).toBe(true);
    });

    it('should return false when no token exists', () => {
      localStorageMock.getItem.mockReturnValue(null);
      expect(authService.isAuthenticated()).toBe(false);
    });
  });
});
