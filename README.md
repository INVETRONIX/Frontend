# Frontend Invetronix

Frontend para el sistema de gestión de inventario Invetronix.

## 🚀 Características

- Sistema de autenticación y autorización
- Gestión de productos
- Sistema de compras
- Gestión de usuarios
- Integración con Gemini AI
- Gestión de imágenes

## 📋 Prerrequisitos

- Java 17 o superior
- Maven
- MariaDB

## 👥 Credenciales por Defecto

### Administrador
- **Correo:** admin@invetronix.com
- **Contraseña:** admin123
- **Rol:** ADMIN

## 📁 Estructura del Proyecto

```
frontend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── project/
│   │   │           └── frontend/
│   │   │               ├── core/            # Componentes core
│   │   │               ├── SYSTEMproductos/ # Sistema de productos
│   │   │               ├── SYSTEMregistro/  # Sistema de registro
│   │   │               ├── SYSTEMlogin/     # Sistema de login
│   │   │               ├── SYSTEMcompras/   # Sistema de compras
│   │   │               ├── SYSTEMgemini/    # Integración con Gemini AI
│   │   │               └── SYSTEMimages/    # Gestión de imágenes
│   │   └── resources/
│   └── test/
└── pom.xml
```

## 🔐 Roles y Permisos

### Administrador
- Acceso total al sistema
- Gestión de usuarios
- Gestión de productos
- Gestion de compras

### Cliente
- Realizar compras
- Ver productos

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot
- JavaFX
- MariaDB
- Maven
- JWT
- SWAGGER
- RETROFIT
- DOCKER
- AZURE
- RENDER.COM
- GIT Y GITHUB (GITFLOW)
- PATRON REPOSITORY

## 📝 Notas Adicionales

- La aplicación requiere una conexión a internet para la integración con Gemini AI y UNSPLASH
- Se recomienda cambiar las credenciales del .env despues de la instalación

ejemplo:

    DB_URL=ruta base de datos
    DB_USER=usuario
    DB_PASS=contraseña
    JWT_SECRET=a0fcd1240ef32a13f09788e81befb94964448d1559053f169234dfcf8955e2ea
    GEMINI_API_KEY=api key de gemini
