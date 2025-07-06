module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'jsdom', // o 'node' si no pruebas componentes de UI
  setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'], // Opcional, para configuraciones globales de prueba
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/src/$1', // Si usas alias como @/
  },
  transform: {
    '^.+\.tsx?$': 'ts-jest',
  },
};