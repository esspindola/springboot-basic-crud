package com.francisco.api.demo.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un producto.
 * Se extiende de RuntimeException para ser una excepción no verificada
 */
public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con ID: " + id);
    }

    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
