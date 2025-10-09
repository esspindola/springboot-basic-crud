package com.francisco.api.demo.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francisco.api.demo.exception.InvalidProductoException;
import com.francisco.api.demo.exception.ProductoNotFoundException;
import com.francisco.api.demo.model.Producto;
import com.francisco.api.demo.repository.ProductoRepository;
import com.francisco.api.demo.service.ProductoService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de productos. Tiene toda la lógica de negocio
 * para la gestión de productos. Usa @RequiredArgsConstructor de Lombok para
 * inyección por constructor
 */
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> obtenerTodosPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> obtenerActivosPaginado(Pageable pageable) {
        return productoRepository.findByActivoTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    @Override
    @Transactional
    public Producto crear(Producto producto) {
        validarProducto(producto);

        // Verifica que no exista un producto con el mismo nombre
        productoRepository.findByNombreIgnoreCase(producto.getNombre())
                .ifPresent(p -> {
                    throw new InvalidProductoException(
                            "Ya existe un producto con el nombre: " + producto.getNombre());
                });

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto productoExistente = obtenerPorId(id);

        validarProducto(productoActualizado);

        // Verifica que el nuevo nombre no esté en uso por otro producto
        if (!productoExistente.getNombre().equalsIgnoreCase(productoActualizado.getNombre())) {
            productoRepository.findByNombreIgnoreCase(productoActualizado.getNombre())
                    .ifPresent(p -> {
                        throw new InvalidProductoException(
                                "Ya existe otro producto con el nombre: " + productoActualizado.getNombre());
                    });
        }

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setActivo(productoActualizado.getActivo());

        return productoRepository.save(productoExistente);
    }

    @Override
    @Transactional
    public Producto actualizarParcial(Long id, Map<String, Object> campos) {
        Producto producto = obtenerPorId(id);

        campos.forEach((campo, valor) -> {
            try {
                Field field = Producto.class.getDeclaredField(campo);
                field.setAccessible(true);

                // Convierte el valor al tipo correcto según el campo
                Object valorConvertido = convertirValor(campo, valor);
                field.set(producto, valorConvertido);

            } catch (NoSuchFieldException e) {
                throw new InvalidProductoException("El campo '" + campo + "' no existe en Producto");
            } catch (IllegalAccessException e) {
                throw new InvalidProductoException("No se puede acceder al campo '" + campo + "'");
            } catch (Exception e) {
                throw new InvalidProductoException(
                        "Error al actualizar el campo '" + campo + "': " + e.getMessage());
            }
        });

        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarFisico(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerConStockBajo(Integer stockMinimo) {
        if (stockMinimo < 0) {
            throw new InvalidProductoException("El stock mínimo no puede ser negativo");
        }
        return productoRepository.findByStockLessThanEqual(stockMinimo);
    }

    /**
     * Valida que los datos del producto sean correctos. Aplica el principio DRY
     * para evitar duplicar validaciones
     */
    private void validarProducto(Producto producto) {
        if (producto.getPrecio() != null && producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductoException("El precio debe ser mayor a 0");
        }

        if (producto.getStock() != null && producto.getStock() < 0) {
            throw new InvalidProductoException("El stock no puede ser negativo");
        }
    }

    /**
     * Convierte el valor recibido al tipo apropiado según el campo. Necesario
     * para el método PATCH
     */
    private Object convertirValor(String campo, Object valor) {
        switch (campo) {
            case "precio":
                if (valor instanceof Number) {
                    return new BigDecimal(valor.toString());
                }
                return new BigDecimal((String) valor);

            case "stock":
                if (valor instanceof Number) {
                    return ((Number) valor).intValue();
                }
                return Integer.parseInt(valor.toString());

            case "activo":
                if (valor instanceof Boolean) {
                    return valor;
                }
                return Boolean.parseBoolean(valor.toString());

            case "nombre":
            case "descripcion":
                return valor.toString();

            default:
                return valor;
        }
    }
}
