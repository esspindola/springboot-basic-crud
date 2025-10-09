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

    Page<Producto> findByActivoTrue(Pageable pageable);

    Optional<Producto> findByNombreIgnoreCase(String nombre);

    List<Producto> findByStockLessThanEqual(Integer stock);
}
