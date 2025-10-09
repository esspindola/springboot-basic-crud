package com.francisco.api.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.francisco.api.demo.model.Producto;

public interface ProductoService {

    Page<Producto> obtenerTodosPaginado(Pageable pageable);

    Page<Producto> obtenerActivosPaginado(Pageable pageable);

    Producto obtenerPorId(Long id);

    Producto crear(Producto producto);

    Producto actualizar(Long id, Producto producto);

    Producto actualizarParcial(Long id, Map<String, Object> campos);

    // Soft delete
    void eliminar(Long id);

    // Hard delete
    void eliminarFisico(Long id);

    List<Producto> obtenerConStockBajo(Integer stockMinimo);
}
