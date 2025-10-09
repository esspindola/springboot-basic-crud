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

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerStockBajo(
            @RequestParam(defaultValue = "10") Integer stockMinimo) {

        List<Producto> productos = productoService.obtenerConStockBajo(stockMinimo);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos) {

        Producto productoActualizado = productoService.actualizarParcial(id, campos);
        return ResponseEntity.ok(productoActualizado);
    }

    // Soft delete - marca como inactivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Producto eliminado exitosamente");
        respuesta.put("id", id.toString());

        return ResponseEntity.ok(respuesta);
    }

    // Hard delete - borra permanentemente de la BD
    @DeleteMapping("/{id}/fisico")
    public ResponseEntity<Void> eliminarFisico(@PathVariable Long id) {
        productoService.eliminarFisico(id);
        return ResponseEntity.noContent().build();
    }
}
