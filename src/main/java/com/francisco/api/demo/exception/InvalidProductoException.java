package com.francisco.api.demo.exception;

/**
 * Excepción personalizada para validaciones de negocio del producto
 */
public class InvalidProductoException extends RuntimeException {

    public InvalidProductoException(String mensaje) {
        super(mensaje);
    }
}
