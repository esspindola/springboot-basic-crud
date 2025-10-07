# 🚀 Guía Rápida de Uso

## Inicio Rápido

### 1️⃣ Compilar y Ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

### 2️⃣ Verificar que funciona

Abre tu navegador en: `http://localhost:8080/api/productos`

---

## 📋 Endpoints Principales

### Obtener todos los productos

```bash
GET http://localhost:8080/api/productos
```

### Crear un producto

```bash
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Nuevo Producto",
  "descripcion": "Descripción del producto",
  "precio": 99.99,
  "stock": 50
}
```

### Actualizar precio y stock (PATCH)

```bash
PATCH http://localhost:8080/api/productos/1
Content-Type: application/json

{
  "precio": 89.99,
  "stock": 45
}
```

### Eliminar producto (lógico - marca como inactivo)

```bash
DELETE http://localhost:8080/api/productos/1
# Respuesta: 200 OK con mensaje
# El producto queda en la BD pero con activo=false
```

### Eliminar producto físicamente (lo borra de la BD)

```bash
DELETE http://localhost:8080/api/productos/1/fisico
# Respuesta: 204 No Content (sin cuerpo)
# El producto se elimina permanentemente de la base de datos
```

---

## 🔧 Herramientas Disponibles

### Swagger UI (Documentación Interactiva)

```
http://localhost:8080/swagger-ui.html
```

- Ver todos los endpoints
- Probar la API desde el navegador
- Ver esquemas de datos

### Consola H2 (Base de Datos)

```
http://localhost:8080/h2-console
```

**Configuración:**

- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Password: _(dejar vacío)_

### Colección Postman

**¿Qué es?** Archivo JSON con todos los endpoints de la API ya configurados para usar con [Postman](https://www.postman.com/downloads/).

**¿Cómo importarlo?**
1. Abre Postman
2. Click en **"Import"** (esquina superior izquierda)
3. Selecciona el archivo `postman-collection.json`
4. ¡Listo! Verás todos los endpoints organizados

**Incluye:**
- Todos los métodos GET, POST, PUT, PATCH, DELETE
- Ejemplos de datos para crear/actualizar productos
- Casos de error para probar validaciones

---

## 📊 Datos Iniciales

La aplicación viene con 10 productos de ejemplo:

1. Laptop Dell XPS 13
2. Mouse Logitech MX Master 3
3. Teclado Mecánico Corsair K95
4. Monitor LG UltraWide 34"
5. Webcam Logitech C920
6. Audífonos Sony WH-1000XM4
7. SSD Samsung 970 EVO 1TB
8. Impresora HP LaserJet Pro
9. Router ASUS RT-AX88U
10. Hub USB-C Anker

---

## ✅ Validaciones Implementadas

### Campo: nombre

- ✅ Obligatorio
- ✅ Entre 3 y 100 caracteres
- ✅ Único (no se permiten duplicados)

### Campo: precio

- ✅ Obligatorio
- ✅ Mayor a 0

### Campo: stock

- ✅ No puede ser negativo
- ✅ Por defecto: 0

### Campo: activo

- ✅ Por defecto: true
- ✅ Se usa para eliminación lógica

---

## 🎯 Ejemplos de Uso

### Buscar productos activos

```
GET http://localhost:8080/api/productos?soloActivos=true
```

### Buscar productos con stock bajo

```
GET http://localhost:8080/api/productos/stock-bajo?stockMinimo=10
```

### Obtener un producto específico

```
GET http://localhost:8080/api/productos/1
```

---

#### Códigos de Estado HTTP

La API utiliza los siguientes códigos de estado:

- **200 OK**: Operación exitosa (GET, PUT, PATCH, DELETE lógico)
- **201 Created**: Recurso creado exitosamente (POST)
- **204 No Content**: Eliminación física exitosa (DELETE físico - sin cuerpo de respuesta)
- **400 Bad Request**: Datos inválidos o error de validación
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error del servidor

#### Manejo de Errores

La API devuelve respuestas claras en caso de error:

### Producto no encontrado (404)

```json
{
  "timestamp": "2025-10-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "No se encontró el producto con ID: 999",
  "path": "/api/productos/999"
}
```

### Validación fallida (400)

```json
{
  "timestamp": "2025-10-07T10:30:00",
  "status": 400,
  "error": "Validation Error",
  "mensaje": "Error en la validación de los datos",
  "path": "/api/productos",
  "detalles": [
    "nombre: El nombre del producto es obligatorio",
    "precio: El precio es obligatorio"
  ]
}
```

---

## 🛠️ Comandos Maven Útiles

```bash
# Compilar sin ejecutar tests
mvn clean install -DskipTests

# Solo compilar
mvn compile

# Limpiar archivos compilados
mvn clean

# Ejecutar la aplicación
mvn spring-boot:run

# Ver dependencias
mvn dependency:tree
```

---

## 📝 Notas Importantes

- La base de datos H2 está en **memoria**, los datos se pierden al reiniciar
- El archivo `data.sql` se ejecuta automáticamente al iniciar
- Lombok requiere configuración del IDE (plugin instalado)

---

## 🐛 Solución de Problemas

### Error: "Port 8080 is already in use"

Cambia el puerto en `application.properties`:

```properties
server.port=8081
```

### Error: Lombok no funciona

1. Verifica que el plugin de Lombok esté instalado en tu IDE
2. Habilita "Annotation Processing" en configuración

### No se cargan los datos iniciales

Verifica que en `application.properties` esté:

```properties
spring.jpa.defer-datasource-initialization=true
```

---

## 📚 Recursos Adicionales

- **README.md**: Documentación completa del proyecto
- **DOCUMENTO_EXPLICATIVO.md**: Decisiones de diseño y principios aplicados
- **postman-collection.json**: Colección de Postman con todos los endpoints

---

¡Listo para usar! 🎉
