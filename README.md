🎓 Clases Prácticas

> Proyecto personal desarrollado como parte de mi trabajo freelance. Una aplicación web para la gestión de clases prácticas interactivas, enfocada en modularidad, buenas prácticas de arquitectura y escalabilidad.

📌 Descripción

**Clases Prácticas** es una aplicación web desarrollada con tecnologías Java y React, orientada a facilitar la gestión de información relacionada con clases prácticas educativas. Este proyecto forma parte de mi portafolio personal como desarrollador **Java Full Stack**, y busca demostrar mis habilidades técnicas en el desarrollo de aplicaciones escalables y bien estructuradas.

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JUnit 5 (85% de cobertura)**
- **Maven**
- **Git / GitHub**

### Frontend
- **React**
- **Tailwind CSS**
- **React Router**
- **Axios**

### DevOps y Herramientas
- **GitHub Actions (próximamente CI/CD)**
- **Postman (para pruebas de API)**

📋 Funcionalidades Principales

- Gestión de docentes, estudiantes y guías educativas.
- Autenticación y autorización con JWT.
- Integración con base de datos SQL.
- APIs RESTful con documentación Swagger (OpenAPI).
- Cobertura de pruebas unitarias y de integración con JUnit 5.
- Aplicación de principios SOLID y patrones de diseño como DTO, Repository y Service.

🧪 Pruebas

El proyecto cuenta con un alto nivel de cobertura de pruebas:
- **85% de cobertura en el backend** con JUnit 5.
- Pruebas de controladores, servicios y repositorios.
- Uso de mocks y data providers para validar diferentes escenarios.

📁 Estructura del Proyecto
clases-practicas/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com.albornoke.clasespracticas
│   │   │   │       ├── controlador/
│   │   │   │       ├── servicio/
│   │   │   │       ├── repositorio/
│   │   │   │       ├── modelo/
│   │   │   │       ├── dto/
│   │   │   │       ├── config/
│   │   │   │       └── ClasesPracticasApplication.java
│   │   │   └── recursos/
│   │   └── prueba/
│   └── pom.xml
├── frontend/
│   ├── público/
│   ├── src/
│   │   ├── activos/
│   │   ├── componentes/
│   │   ├── páginas/
│   │   ├── rutas/
│   │   ├── servicios/
│   │   └── App.jsx
│   └── package.json
├── README.md
└── .gitignore
