
import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { authService, User } from '@/services/authService';
import { useNavigate } from 'react-router-dom';

interface AuthContextType {
  user: User | null;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuth = async () => {
      if (authService.isAuthenticated()) {
        try {
          const currentUser = await authService.getCurrentUser();
          console.log('Usuario actual (desde checkAuth):', currentUser); // Para depuración
          setUser(currentUser);
          // Redirect if user is already logged in and on the main page
          if (window.location.pathname === '/' || window.location.pathname === '/login') {
            redirectBasedOnRole(currentUser.rol);
          }
        } catch (error) {
          console.error('Error checking authentication:', error);
          authService.logout();
        }
      }
      setIsLoading(false);
    };

    checkAuth();
  }, []);

  const redirectBasedOnRole = (userRole: string) => {
    console.log('Redirigiendo basado en el rol:', userRole); // Para depuración
    switch (userRole) {
      case 'ADMIN':
        navigate('/admin');
        break;
      case 'DOCENTE':
        navigate('/docente');
        break;
      case 'ESTUDIANTE':
        navigate('/estudiante');
        break;
      default:
        navigate('/');
        break;
    }
  };

  const login = async (email: string, password: string) => {
    try {
      const { user: loggedInUser } = await authService.login({ email, password });
      console.log('Usuario recibido (desde login):', loggedInUser); // Para depuración
      setUser(loggedInUser);
      redirectBasedOnRole(loggedInUser.rol);
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
    navigate('/login');
  };

  const value = {
    user,
    isLoading,
    login,
    logout,
    isAuthenticated: !!user,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
