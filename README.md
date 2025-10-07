# API RESTful de Gestión de Productos

## Descripción del Proyecto

API RESTful desarrollada con **Java 17** y **Spring Boot 3.5.6** para la gestión completa de productos. Este proyecto implementa todas las buenas prácticas y principios aprendidos en las clases 1 a 5, incluyendo arquitectura limpia, inyección de dependencias, manejo de excepciones y operaciones CRUD completas.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.6**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring DevTools
- **Base de datos H2** (en memoria)
- **Lombok** (reducir código boilerplate)
- **SpringDoc OpenAPI** (documentación automática)
- **Maven** (gestión de dependencias)

## Estructura del Proyecto

```
src/main/java/com/francisco/api/demo/
├── controller/
│   └── ProductoController.java       # Endpoints REST
├── service/
│   ├── ProductoService.java          # Interfaz del servicio
│   └── impl/
│       └── ProductoServiceImpl.java  # Implementación del servicio
├── repository/
│   └── ProductoRepository.java       # Acceso a datos con JPA
├── model/
│   └── Producto.java                 # Entidad JPA
├── exception/
│   ├── ProductoNotFoundException.java
│   ├── InvalidProductoException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java   # Manejo global de excepciones
└── DemoApplication.java              # Clase principal
```

## Modelo de Datos: Producto

| Campo              | Tipo          | Descripción                         |
| ------------------ | ------------- | ----------------------------------- |
| id                 | Long          | Identificador único (auto-generado) |
| nombre             | String        | Nombre del producto (3-100 chars)   |
| descripcion        | String        | Descripción del producto (max 500)  |
| precio             | BigDecimal    | Precio del producto (> 0)           |
| stock              | Integer       | Cantidad en inventario (>= 0)       |
| fechaCreacion      | LocalDateTime | Fecha de creación automática        |
| fechaActualizacion | LocalDateTime | Fecha de última modificación        |
| activo             | Boolean       | Estado del producto (default: true) |

## Endpoints de la API

### Base URL: `http://localhost:8080/api/productos`

| Método | Endpoint                                   | Descripción                                 |
| ------ | ------------------------------------------ | ------------------------------------------- |
| GET    | `/api/productos`                           | Obtener todos los productos                 |
| GET    | `/api/productos?page=0`                    | Obtener productos (página 0, 10 por página) |
| GET    | `/api/productos?soloActivos=true`          | Obtener solo productos activos              |
| GET    | `/api/productos/{id}`                      | Obtener un producto por ID                  |
| GET    | `/api/productos/stock-bajo?stockMinimo=10` | Productos con stock bajo                    |
| POST   | `/api/productos`                           | Crear un nuevo producto                     |
| PUT    | `/api/productos/{id}`                      | Actualizar completamente un producto        |
| PATCH  | `/api/productos/{id}`                      | Actualizar parcialmente un producto         |
| DELETE | `/api/productos/{id}`                      | Eliminar lógicamente un producto            |
| DELETE | `/api/productos/{id}/fisico`               | Eliminar físicamente un producto            |

### Ejemplos de Uso

#### 1. Obtener todos los productos

```bash
GET http://localhost:8080/api/productos
```

#### 2. Crear un nuevo producto

```bash
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Attack on Titan Vol. 1",
  "descripcion": "La humanidad lucha por sobrevivir contra titanes gigantes",
  "precio": 10.99,
  "stock": 50
}
```

#### 3. Actualizar completamente un producto

```bash
PUT http://localhost:8080/api/productos/1
Content-Type: application/json

{
  "nombre": "Alya Sometimes Hides Her Feelings in Russian Vol. 1 (Actualizado)",
  "descripcion": "Romance y comedia sobre Alya - Edición especial",
  "precio": 12.99,
  "stock": 45,
  "activo": true
}
```

#### 4. Actualizar parcialmente un producto (PATCH)

```bash
PATCH http://localhost:8080/api/productos/1
Content-Type: application/json

{
  "precio": 11.99,
  "stock": 48
}
```

#### 5. Eliminar un producto (lógico)

```bash
DELETE http://localhost:8080/api/productos/1
# Respuesta: 200 OK con mensaje de confirmación
```

#### 6. Eliminar un producto físicamente

```bash
DELETE http://localhost:8080/api/productos/1/fisico
# Respuesta: 204 No Content (sin cuerpo de respuesta)
```

## Principios y Buenas Prácticas Aplicadas

### 1. Código Limpio (Clase 1)

- ✅ Nombres descriptivos y coherentes
- ✅ Principio **DRY** (Don't Repeat Yourself) - método `validarProducto()`
- ✅ Principio **KISS** (Keep It Simple, Stupid) - soluciones simples
- ✅ Comentarios claros y útiles en métodos públicos
- ✅ Manejo estructurado de excepciones personalizadas

### 2. Configuración Spring Boot (Clase 2)

- ✅ Proyecto generado con Spring Initializr
- ✅ Estructura organizada por capas (controller, service, repository, model)
- ✅ Configuración en `application.properties`
- ✅ Gestión de dependencias con Maven

### 3. API REST (Clase 3)

- ✅ Endpoints RESTful con convenciones estándar
- ✅ Operaciones CRUD completas (GET, POST, PUT, PATCH, DELETE)
- ✅ Códigos HTTP apropiados (200, 201, 204, 404, 400, 500)
- ✅ Mensajes de error claros con `ErrorResponse`
- ✅ Patrón MVC correctamente implementado

### 4. Inyección de Dependencias (Clase 4)

- ✅ Inyección por constructor con `@RequiredArgsConstructor` (Lombok)
- ✅ Uso de interfaces (`ProductoService`) para bajo acoplamiento
- ✅ Anotaciones: `@RestController`, `@Service`, `@Repository`
- ✅ Manejo de rutas con `@RequestMapping`, `@GetMapping`, etc.
- ✅ Uso correcto de `@PathVariable` y `@RequestBody`

### 5. Construcción Completa (Clase 5)

- ✅ Entidad JPA con anotaciones (`@Entity`, `@Id`, `@GeneratedValue`)
- ✅ Repositorio con `JpaRepository` y métodos personalizados
- ✅ Lógica de negocio en capa de servicio separada
- ✅ Controlador REST con todos los endpoints
- ✅ Archivo `data.sql` con datos iniciales

## Funcionalidades Extra (Puntos Adicionales)

### ✨ Manejo Avanzado de Excepciones

- `@ControllerAdvice` con `GlobalExceptionHandler`
- Excepciones personalizadas: `ProductoNotFoundException`, `InvalidProductoException`
- Respuestas de error estructuradas con `ErrorResponse`

### ✨ Validaciones con Bean Validation

- `@NotBlank`, `@NotNull`, `@Size`, `@Min`, `@DecimalMin`
- Validación automática con `@Valid`

### ✨ Operación PATCH

- Actualización parcial de atributos con `Map<String, Object>`
- Conversión dinámica de tipos

### ✨ Documentación con Swagger/OpenAPI

- Acceso a documentación interactiva en: `http://localhost:8080/swagger-ui.html`
- Especificación OpenAPI en: `http://localhost:8080/api-docs`

### ✨ Eliminación Lógica y Física

- DELETE estándar: marca el producto como inactivo
- DELETE /fisico: elimina permanentemente de la base de datos

### ✨ Paginación

- 10 productos por página
- Parámetro `page` (ejemplo: `?page=0`)
- Respuesta incluye metadata de paginación

## Instrucciones de Ejecución

### Requisitos Previos

- **JDK 17** instalado
- **Maven** instalado
- Variable de entorno `JAVA_HOME` configurada

### Pasos para Ejecutar

1. **Clonar o descargar el proyecto**

2. **Compilar el proyecto**

   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**

   ```bash
   mvn spring-boot:run
   ```

   O ejecutar el JAR generado:

   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

4. **Verificar que la aplicación esté corriendo**

   - Consola: Buscar mensaje "Started DemoApplication"
   - Navegador: `http://localhost:8080/api/productos`

5. **Acceder a herramientas adicionales**
   - **Consola H2**: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Usuario: `sa`
     - Password: _(dejar vacío)_
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## Pruebas con Postman

Se incluye el archivo **`postman-collection.json`** con todos los endpoints ya configurados.

### Cómo usar:

1. Descarga e instala [Postman](https://www.postman.com/downloads/)
2. Abre Postman y haz click en **"Import"**
3. Selecciona el archivo `postman-collection.json`
4. Se cargarán automáticamente todos los endpoints de la API
5. Click en cualquier endpoint → **"Send"** para probar

### Endpoints incluidos:

- ✅ Obtener todos los productos
- ✅ Crear producto nuevo
- ✅ Actualizar completo (PUT)
- ✅ Actualizar parcial (PATCH)
- ✅ Eliminar lógico y físico
- ✅ Casos de error (404, 400)

## Decisiones de Diseño

### Arquitectura en Capas

Se implementó una arquitectura de 4 capas para lograr separación de responsabilidades:

- **Controller**: Manejo de HTTP y validación de entrada
- **Service**: Lógica de negocio y reglas de validación
- **Repository**: Acceso a datos
- **Model**: Entidades y DTOs

### Inyección de Dependencias

Se utilizó inyección por constructor con `@RequiredArgsConstructor` de Lombok para:

- Inmutabilidad de dependencias
- Facilitar testing con mocks
- Cumplir con el principio de inversión de dependencias

### Manejo de Excepciones

Se implementó un manejador global con `@ControllerAdvice` para:

- Centralizar el manejo de errores
- Mantener controladores limpios
- Proveer respuestas consistentes

### Base de Datos H2

Se eligió H2 en memoria para:

- Facilitar desarrollo y pruebas
- No requerir instalación de base de datos
- Reiniciar datos en cada ejecución

## Autor

**Francisco Espíndola**  
Parcial 1 - Programación  
Octubre 2025

## Licencia

Este proyecto es de uso académico y educativo.
