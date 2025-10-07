package com.francisco.api.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.francisco.api.demo.model.Producto;
import com.francisco.api.demo.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar los endpoints de Producto. Implementa todas
 * las operaciones CRUD siguiendo las convenciones REST. Utiliza inyección de
 * dependencias por constructor con @RequiredArgsConstructor
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * GET /api/productos Obtiene todos los productos del sistema con paginación
     * (10 por página)
     *
     * @param page Número de página (inicia en 0)
     * @param soloActivos Parámetro opcional para filtrar solo productos activos
     * @return Página de productos con código 200 OK
     */
    @GetMapping
    public ResponseEntity<Page<Producto>> obtenerTodos(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "false") Boolean soloActivos) {

        Pageable pageable = PageRequest.of(page, 10);

        Page<Producto> productos = soloActivos
                ? productoService.obtenerActivosPaginado(pageable)
                : productoService.obtenerTodosPaginado(pageable);

        return ResponseEntity.ok(productos);
    }

    /**
     * GET /api/productos/{id} Obtiene un producto específico por su ID.
     *
     * @param id ID del producto
     * @return Producto encontrado con código 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * GET /api/productos/stock-bajo Obtiene productos con stock bajo o igual al
     * especificado
     *
     * @param stockMinimo Stock mínimo a considerar (defecto es 10o)
     * @return Lista de productos con stock bajo
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerStockBajo(
            @RequestParam(defaultValue = "10") Integer stockMinimo) {

        List<Producto> productos = productoService.obtenerConStockBajo(stockMinimo);
        return ResponseEntity.ok(productos);
    }

    /**
     * POST /api/productos Crea un nuevo producto en el sistema.
     *
     * @param producto Datos del producto a crear (validados con @Valid)
     * @return Producto creado con código 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    /**
     * PUT /api/productos/{id} Actualiza completamente un producto existente
     *
     * @param id ID del producto a actualizar
     * @param producto Nuevos datos del producto
     * @return Producto actualizado con código 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * PATCH /api/productos/{id} Actualiza parcialmente uno o varios atributos
     * de un producto
     *
     * @param id ID del producto a actualizar
     * @param campos Mapa con los campos a actualizar
     * @return Producto actualizado con código 200 OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos) {

        Producto productoActualizado = productoService.actualizarParcial(id, campos);
        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * DELETE /api/productos/{id} Elimina lógicamente un producto (soft delete).
     * El producto se marca como inactivo pero permanece en la base de datos
     *
     * @param id ID del producto a eliminar
     * @return Respuesta con código 200 OK y mensaje de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Producto eliminado exitosamente");
        respuesta.put("id", id.toString());

        return ResponseEntity.ok(respuesta);
    }

    /**
     * DELETE /api/productos/{id}/fisico Elimina físicamente un producto de la
     * base de datos (hard delete). El producto se borra permanentemente y no se
     * puede recuperar
     *
     * @param id ID del producto a eliminar
     * @return Respuesta con código 204 NO CONTENT (osea sin cuerpo de respuesta)
     */
    @DeleteMapping("/{id}/fisico")
    public ResponseEntity<Void> eliminarFisico(@PathVariable Long id) {
        productoService.eliminarFisico(id);
        return ResponseEntity.noContent().build();
    }
}
