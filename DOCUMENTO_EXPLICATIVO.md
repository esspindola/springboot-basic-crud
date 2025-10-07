# Documento Explicativo - API RESTful de Productos

## Parcial 1 - Programación

**Autor:** Francisco Espíndola  
**Fecha:** Octubre 2025  
**Materia:** Programación 1

---

## 1. INTRODUCCIÓN

Este documento explica las decisiones de diseño tomadas durante el desarrollo de la API RESTful de gestión de productos, así como la aplicación de los principios y buenas prácticas aprendidas en las clases 1 a 5.

---

## 2. DECISIONES DE DISEÑO

### 2.1 Elección de la Entidad: Producto

Se eligió la entidad **Producto** porque:

- Es un dominio familiar y fácil de entender
- Permite demostrar todos los requisitos del examen
- Representa un caso de uso real en sistemas de e-commerce
- Facilita la implementación de validaciones complejas

### 2.2 Arquitectura del Proyecto

Se implementó una **arquitectura en capas** siguiendo el patrón MVC:

```
┌─────────────────────┐
│   Controller        │  ← Capa de presentación (HTTP)
├─────────────────────┤
│   Service           │  ← Lógica de negocio
├─────────────────────┤
│   Repository        │  ← Acceso a datos
├─────────────────────┤
│   Model             │  ← Entidades
└─────────────────────┘
```

**Ventajas:**

- Separación clara de responsabilidades
- Facilita el mantenimiento y testing
- Permite escalar el proyecto fácilmente
- Cumple con el principio de **Single Responsibility**

### 2.3 Base de Datos H2

Se eligió H2 en memoria por:

- ✅ No requiere instalación adicional
- ✅ Ideal para desarrollo y pruebas
- ✅ Datos se reinician en cada ejecución (ambiente limpio)
- ✅ Consola web integrada para verificar datos

### 2.4 Lombok

Se utilizó Lombok para:

- Reducir código boilerplate (getters, setters, constructores)
- Inyección de dependencias simplificada con `@RequiredArgsConstructor`
- Mantener las clases limpias y legibles
- Cumplir con el principio **KISS**

---

## 3. APLICACIÓN DE PRINCIPIOS Y BUENAS PRÁCTICAS

### 3.1 Clase 1: Buenas Prácticas y Principios Básicos

#### ✅ Código Limpio y Legible

- **Nombres descriptivos:** `ProductoService`, `obtenerPorId()`, `actualizarParcial()`
- **Sin abreviaciones confusas:** Se evitó usar `prod`, `repo`, etc.
- **Métodos cortos:** Cada método tiene una única responsabilidad

#### ✅ Principio DRY (Don't Repeat Yourself)

Ejemplo en `ProductoServiceImpl`:

```java
private void validarProducto(Producto producto) {
  // Validación reutilizable en crear() y actualizar()
  if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
    throw new InvalidProductoException("El precio debe ser mayor a 0");
  }
  // ...
}
```

#### ✅ Principio KISS (Keep It Simple, Stupid (perdón por la mala palabra))

- Se evitó sobre-ingeniería
- Soluciones directas y comprensibles
- No se agregaron abstracciones innecesarias

#### ✅ Manejo de Excepciones Personalizado

- `ProductoNotFoundException`: Para recursos no encontrados
- `InvalidProductoException`: Para validaciones de negocio
- `GlobalExceptionHandler`: Manejo centralizado con `@ControllerAdvice`

#### ✅ Comentarios Útiles

- Javadoc en métodos públicos
- Comentarios explicativos en lógica compleja
- Sin comentarios redundantes

---

### 3.2 Clase 2: Configuración de Spring Boot

#### ✅ Proyecto Generado con Spring Initializr

Dependencias incluidas:

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `h2database`
- `lombok`
- `springdoc-openapi` (Swagger)

#### ✅ Estructura del Proyecto Organizada

```
src/main/java/com/francisco/api/demo/
├── controller/     → Endpoints REST
├── service/        → Lógica de negocio
├── repository/     → Acceso a datos
├── model/          → Entidades
└── exception/      → Manejo de errores
```

#### ✅ Configuración en application.properties

```properties
# Base de datos H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true

# OpenAPI/Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

---

### 3.3 Clase 3: Creación de la API REST

#### ✅ Diseño de Endpoints RESTful

| Método HTTP | Endpoint              | Acción              |
| ----------- | --------------------- | ------------------- |
| GET         | `/api/productos`      | Listar todos        |
| GET         | `/api/productos/{id}` | Obtener por ID      |
| POST        | `/api/productos`      | Crear nuevo         |
| PUT         | `/api/productos/{id}` | Actualizar completo |
| PATCH       | `/api/productos/{id}` | Actualizar parcial  |
| DELETE      | `/api/productos/{id}` | Eliminar (lógico)   |
| DELETE      | `/api/productos/{id}/fisico` | Eliminar (físico)   |

#### ✅ Códigos HTTP Apropiados

- **200 OK:** Operación exitosa (GET, PUT, PATCH, DELETE lógico)
- **201 CREATED:** Recurso creado exitosamente (POST)
- **204 NO CONTENT:** Eliminación física exitosa (DELETE /fisico - sin cuerpo de respuesta)
- **400 BAD REQUEST:** Datos inválidos o error de validación
- **404 NOT FOUND:** Recurso no encontrado
- **500 INTERNAL SERVER ERROR:** Error del servidor

#### ✅ Mensajes de Error Claros

Estructura de respuesta de error (`ErrorResponse`):

```json
{
  "timestamp": "2025-10-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "No se encontró el producto con ID: 999",
  "path": "/api/productos/999",
  "detalles": []
}
```

#### ✅ Patrón MVC Implementado

- **Modelo:** `Producto.java` (entidad JPA)
- **Vista:** Respuestas JSON (REST)
- **Controlador:** `ProductoController.java`

---

### 3.4 Clase 4: Inyección de Dependencias

#### ✅ Inyección por Constructor con Lombok

```java
@RestController
@RequiredArgsConstructor  // Lombok genera constructor
public class ProductoController {
  private final ProductoService productoService; // Inyectado
}
```

**Ventajas:**

- Inmutabilidad de dependencias
- Facilita testing con mocks
- Cumple con el principio de inversión de dependencias (DIP)

#### ✅ Uso de Interfaces para Bajo Acoplamiento

```java
// Interfaz
public interface ProductoService { ... }

// Implementación
@Service
public class ProductoServiceImpl implements ProductoService { ... }

// Controlador depende de la interfaz, no de la implementación
private final ProductoService productoService;
```

#### ✅ Anotaciones de Spring

- `@RestController`: Combina `@Controller` + `@ResponseBody`
- `@Service`: Marca la capa de servicio
- `@Repository`: Marca la capa de acceso a datos
- `@RequestMapping("/api/productos")`: Ruta base
- `@GetMapping`, `@PostMapping`, `@PutMapping`, etc.

#### ✅ Manejo de Parámetros

- `@PathVariable`: Variables en la URL (`/productos/{id}`)
- `@RequestBody`: Cuerpo de la solicitud (JSON)
- `@RequestParam`: Parámetros de consulta (`?soloActivos=true`)

---

### 3.5 Clase 5: Construcción Completa de la API

#### ✅ Modelo de Datos con JPA

```java
@Entity
@Table(name = "productos")
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100)
  private String nombre;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal precio;

  // ... más campos
}
```

#### ✅ Repositorio con JpaRepository

```java
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
  List<Producto> findByActivoTrue();
  Optional<Producto> findByNombreIgnoreCase(String nombre);
  List<Producto> findByStockLessThanEqual(Integer stock);
}
```

**Ventajas:**

- Métodos CRUD automáticos (`save`, `findById`, `deleteById`, etc.)
- Queries derivadas del nombre del método
- No se necesita código SQL manualmente

#### ✅ Servicios con Lógica de Negocio

Separación clara:

- **Controller:** Solo maneja HTTP
- **Service:** Contiene toda la lógica de validación y negocio
- **Repository:** Solo acceso a datos

#### ✅ Inicialización de Datos con data.sql

```sql
INSERT INTO productos (nombre, descripcion, precio, stock, ...)
VALUES ('Laptop Dell XPS 13', '...', 1299.99, 15, ...);
```

Configuración necesaria:

```properties
spring.jpa.defer-datasource-initialization=true
```

---

## 4. FUNCIONALIDADES EXTRA IMPLEMENTADAS

### 4.1 Manejo Avanzado de Excepciones ⭐

- `@ControllerAdvice` con `GlobalExceptionHandler`
- Captura de múltiples tipos de excepciones
- Respuestas estructuradas y consistentes

### 4.2 Validaciones con Bean Validation ⭐

- `@NotBlank`, `@NotNull`, `@Size`, `@Min`, `@DecimalMin`
- Mensajes de error personalizados
- Validación automática con `@Valid`

### 4.3 Operación PATCH ⭐

Permite actualizar solo campos específicos:

```json
PATCH /api/productos/1
{
  "precio": 1199.99,
  "stock": 12
}
```

### 4.4 Documentación con Swagger/OpenAPI ⭐

Acceso en: `http://localhost:8080/swagger-ui.html`

**Características:**

- Documentación automática de endpoints
- Pruebas directas desde el navegador
- Esquemas JSON generados automáticamente

### 4.5 Eliminación Lógica y Física ⭐

**Eliminación Lógica (Soft Delete):**

- Endpoint: `DELETE /api/productos/{id}`
- Acción: Marca el producto como `activo = false`
- Ventaja: Los datos se conservan para auditoría o históricos
- Respuesta: **200 OK** con mensaje de confirmación

**Eliminación Física (Hard Delete):**

- Endpoint: `DELETE /api/productos/{id}/fisico`
- Acción: Elimina permanentemente el registro de la base de datos
- Uso: Cuando se necesita borrar completamente un producto
- Respuesta: **204 NO CONTENT** (sin cuerpo de respuesta, solo headers)

### 4.6 Métodos de Consulta Personalizados ⭐

```java
// Productos con stock bajo
GET /api/productos/stock-bajo?stockMinimo=10

// Solo productos activos
GET /api/productos?soloActivos=true
```

---

## 5. PRUEBAS Y VALIDACIÓN

### 5.1 Herramientas de Prueba

#### Postman

- Colección incluida: `postman-collection.json`
- Pruebas de todos los endpoints
- Casos de éxito y error

#### Swagger UI

- Documentación interactiva
- Probar endpoints desde el navegador
- Ver esquemas de datos

#### Consola H2

- URL: `http://localhost:8080/h2-console`
- Verificar datos en la base de datos
- Ejecutar consultas SQL

### 5.2 Casos de Prueba

#### ✅ Casos de Éxito

1. Crear producto válido → 201 CREATED
2. Obtener producto existente → 200 OK
3. Actualizar producto → 200 OK
4. Eliminar producto → 200 OK

#### ✅ Casos de Error

1. Crear sin nombre → 400 BAD REQUEST
2. Precio negativo → 400 BAD REQUEST
3. Producto inexistente → 404 NOT FOUND
4. Campo inválido en PATCH → 400 BAD REQUEST

---

## 6. INSTRUCCIONES DE EJECUCIÓN

### Requisitos

- JDK 17
- Maven 3.6+
- IDE (IntelliJ, Eclipse, VSCode)

### Pasos

1. **Compilar el proyecto:**

   ```bash
   mvn clean install
   ```

2. **Ejecutar la aplicación:**

   ```bash
   mvn spring-boot:run
   ```

3. **Verificar que funciona:**

   - Ir a: `http://localhost:8080/api/productos`
   - Deberías ver la lista de productos

4. **Probar con Swagger:**

   - Ir a: `http://localhost:8080/swagger-ui.html`

5. **Verificar base de datos:**
   - Ir a: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuario: `sa`
   - Password: _(vacío)_

---

## 7. CONCLUSIONES

Este proyecto demuestra la aplicación práctica de todos los conceptos aprendidos:

✅ **Código limpio:** Nombres descriptivos, métodos cortos, sin duplicación  
✅ **Arquitectura sólida:** Separación en capas, bajo acoplamiento  
✅ **Buenas prácticas:** DRY, KISS, SOLID  
✅ **API RESTful completa:** CRUD + operaciones extra  
✅ **Manejo robusto de errores:** Excepciones personalizadas, mensajes claros  
✅ **Validaciones:** Bean Validation + validaciones de negocio  
✅ **Documentación:** Swagger + comentarios Javadoc  
✅ **Facilidad de uso:** Datos iniciales, configuración simple

El resultado es una API profesional, mantenible y lista para producción.

---

## 8. REFERENCIAS

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Bean Validation](https://beanvalidation.org/)
- [Lombok](https://projectlombok.org/)
- [OpenAPI/Swagger](https://springdoc.org/)

---

**Fin del documento**
