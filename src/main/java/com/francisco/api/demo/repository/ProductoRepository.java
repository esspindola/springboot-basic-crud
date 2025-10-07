package com.francisco.api.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.francisco.api.demo.model.Producto;

/**
 * Repositorio para gestionar las operaciones de acceso a datos de Producto.
 * Extiende JpaRepository para obtener métodos CRUD predefinidos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca productos activos con paginación
     *
     * @param pageable Objeto Pageable para la paginación
     * @return Página de productos activos
     */
    Page<Producto> findByActivoTrue(Pageable pageable);

    /**
     * Busca un producto por su nombre (ignorando mayúsculas o minúsculas)
     *
     * @param nombre Nombre del producto
     * @return Optional con el producto si existe
     */
    Optional<Producto> findByNombreIgnoreCase(String nombre);

    /**
     * Busca productos cuyo stock sea menor o igual al valor especificado
     *
     * @param stock Valor máximo de stock
     * @return Lista de productos con stock bajo
     */
    List<Producto> findByStockLessThanEqual(Integer stock);
}
