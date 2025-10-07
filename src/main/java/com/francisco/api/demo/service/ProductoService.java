package com.francisco.api.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.francisco.api.demo.model.Producto;

/**
 * Interfaz que define los servicios disponibles para la gestión de productos.
 * Aplicando el principio de inversión de dependencias
 */
public interface ProductoService {

    /**
     * Obtiene todos los productos del sistema con paginación (10 por pag)
     *
     * @param pageable Configuración de paginación
     * @return Página de productos
     */
    Page<Producto> obtenerTodosPaginado(Pageable pageable);

    /**
     * Obtiene solo los productos activos con paginación (10 por página)
     *
     * @param pageable Configuración de paginación
     * @return Página de productos activos
     */
    Page<Producto> obtenerActivosPaginado(Pageable pageable);

    /**
     * Busca un producto por su ID
     *
     * @param id ID del producto
     * @return Producto encontrado
     */
    Producto obtenerPorId(Long id);

    /**
     * Crea un nuevo producto en el sistema
     *
     * @param producto Producto a crear
     * @return Producto creado con su ID asignado
     */
    Producto crear(Producto producto);

    /**
     * Actualiza completamente un producto existente
     *
     * @param id ID del producto a actualizar
     * @param producto Datos actualizados del producto
     * @return Producto actualizado
     */
    Producto actualizar(Long id, Producto producto);

    /**
     * Actualiza parcialmente uno o varios atributos de un producto
     *
     * @param id ID del producto a actualizar
     * @param campos Mapa con los campos a actualizar
     * @return Producto actualizado
     */
    Producto actualizarParcial(Long id, Map<String, Object> campos);

    /**
     * Elimina un producto del sistema (eliminación lógica, osea el campo booleano "activo" se pone en false)
     *
     * @param id ID del producto a eliminar
     */
    void eliminar(Long id);

    /**
     * Elimina físicamente un producto de la base de datos
     *
     * @param id ID del producto a eliminar
     */
    void eliminarFisico(Long id);

    /**
     * Obtiene productos con stock bajo o igual al especificado
     *
     * @param stockMinimo Stock mínimo a considerar
     * @return Lista de productos con stock bajo
     */
    List<Producto> obtenerConStockBajo(Integer stockMinimo);
}
