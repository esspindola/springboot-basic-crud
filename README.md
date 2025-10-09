# API RESTful de Gestión de Productos

**Examen Práctico - Parcial 1 - Desarrollo Web**  
**Autor:** Francisco Espíndola
**Profesor:** Gabriel Arriola
**Materia:** Programación 1
**Fecha:** Octubre 2025

## Descripción

API RESTful desarrollada con **Java 17** y **Spring Boot 3.5.6** para gestión de productos. Implementa operaciones CRUD completas, validaciones, manejo de excepciones, paginación y documentación con Swagger.

## Tecnologías

- Java 17
- Spring Boot 3.5.6 (Web, Data JPA, Validation)
- H2 Database (en memoria)
- Lombok
- SpringDoc OpenAPI (Swagger)
- Maven

## Estructura del Proyecto

```
src/main/java/com/francisco/api/demo/
├── controller/      # Endpoints REST
├── service/         # Lógica de negocio
├── repository/      # Acceso a datos
├── model/           # Entidades JPA
└── exception/       # Manejo de errores
```

## Endpoints

**Base URL:** `http://localhost:8080/api/productos`

| Método | Endpoint                     | Descripción                   |
| ------ | ---------------------------- | ----------------------------- |
| GET    | `/api/productos`             | Listar todos (paginado)       |
| GET    | `/api/productos/{id}`        | Obtener por ID                |
| GET    | `/api/productos/stock-bajo`  | Productos con stock bajo      |
| POST   | `/api/productos`             | Crear producto                |
| PUT    | `/api/productos/{id}`        | Actualizar completo           |
| PATCH  | `/api/productos/{id}`        | Actualizar parcial            |
| DELETE | `/api/productos/{id}`        | Eliminar lógico (soft delete) |
| DELETE | `/api/productos/{id}/fisico` | Eliminar físico (hard delete) |

### Ejemplos

**Crear producto:**

```bash
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Producto Ejemplo",
  "descripcion": "Descripción del producto",
  "precio": 99.99,
  "stock": 50
}
```

**Actualizar parcial (PATCH):**

```bash
PATCH http://localhost:8080/api/productos/1
Content-Type: application/json

{
  "precio": 89.99,
  "stock": 45
}
```

## Principios y Buenas Prácticas

### Código Limpio (Clase 1)

- Nombres descriptivos y coherentes
- Principio DRY - método `validarProducto()` reutilizable
- Principio KISS - soluciones simples
- Excepciones personalizadas con mensajes claros

### Configuración Spring Boot (Clase 2)

- Proyecto generado con Spring Initializr
- Estructura en capas (controller, service, repository, model)
- Configuración en `application.properties`
- Gestión de dependencias con Maven

### API REST (Clase 3)

- Endpoints RESTful estándar
- CRUD completo + PATCH
- Códigos HTTP apropiados (200, 201, 204, 404, 400, etc)
- Patrón MVC implementado

### Inyección de Dependencias (Clase 4)

- Inyección por constructor con `@RequiredArgsConstructor`
- Interfaces para bajo acoplamiento
- Anotaciones Spring correctas

### Construcción Completa (Clase 5)

- Entidad JPA con validaciones
- Repositorio con `JpaRepository`
- Lógica de negocio en servicio
- Archivo `data.sql` con datos iniciales

## Funcionalidades Extra

- ✨ Manejo global de excepciones con `@ControllerAdvice`
- ✨ Validaciones con Bean Validation (`@NotBlank`, `@NotNull`, `@Size`, etc)
- ✨ Operación PATCH para actualización parcial
- ✨ Documentación con Swagger/OpenAPI
- ✨ Eliminación lógica (soft delete) y física (hard delete)
- ✨ Paginación (10 por página)

## Decisiones de Diseño

### Arquitectura en Capas

Separación clara de responsabilidades en 4 capas:

- **Controller**: Manejo de HTTP
- **Service**: Lógica de negocio
- **Repository**: Acceso a datos
- **Model**: Entidades JPA

### Inyección de Dependencias

Inyección por constructor con `@RequiredArgsConstructor` para inmutabilidad y facilitar testing.

### Manejo de Excepciones

Manejador global con `@ControllerAdvice` para respuestas de error consistentes.

### Base de Datos H2

Base de datos en memoria para desarrollo sin instalación adicional.

## Cómo Ejecutar

### Requisitos

- JDK 17
- Maven 3.6+

### Pasos

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run

# Verificar
http://localhost:8080/api/productos
```

### Herramientas Disponibles

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Consola H2**: `http://localhost:8080/h2-console`
  - URL: `jdbc:h2:mem:testdb`
  - Usuario: `sa`
  - Password: _(vacío)_

## Pruebas

### Swagger

Documentación interactiva en `http://localhost:8080/swagger-ui.html` para probar todos los endpoints desde el navegador.

## Datos Iniciales

La aplicación incluye 20 productos de ejemplo (mangas) en `data.sql`, cargados automáticamente al iniciar.

---
