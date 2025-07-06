
import '@testing-library/jest-dom';

// Mock de window.location
delete (window as any).location;
window.location = { href: '' } as any;

// Mock de console para tests más limpios
global.console = {
  ...console,
  error: jest.fn(),
  warn: jest.fn(),
};
