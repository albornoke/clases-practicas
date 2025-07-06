
import { renderHook, act } from '@testing-library/react';
import { AuthProvider, useAuth } from '@/hooks/useAuth';
import { authService } from '@/services/authService';

// Mock del servicio de autenticación
jest.mock('@/services/authService');
const mockedAuthService = authService as jest.Mocked<typeof authService>;

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <AuthProvider>{children}</AuthProvider>
);

describe('useAuth Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should initialize with null user', () => {
    mockedAuthService.isAuthenticated.mockReturnValue(false);
    
    const { result } = renderHook(() => useAuth(), { wrapper });
    
    expect(result.current.user).toBeNull();
    expect(result.current.isAuthenticated).toBe(false);
  });

  it('should login user successfully', async () => {
    const mockUser = {
      id: '1',
      name: 'Juan',
      lastName: 'Pérez',
      email: 'juan@test.com',
      role: 'estudiante' as const, // Cambiado de 'student'
    };

    mockedAuthService.login.mockResolvedValue({
      user: mockUser,
      token: 'mock-token',
    });

    const { result } = renderHook(() => useAuth(), { wrapper });

    await act(async () => {
      await result.current.login('juan@test.com', 'password123');
    });

    expect(result.current.user).toEqual(mockUser);
    expect(result.current.isAuthenticated).toBe(true);
  });

  it('should logout user', () => {
    const { result } = renderHook(() => useAuth(), { wrapper });

    act(() => {
      result.current.logout();
    });

    expect(mockedAuthService.logout).toHaveBeenCalled();
    expect(result.current.user).toBeNull();
    expect(result.current.isAuthenticated).toBe(false);
  });
});
