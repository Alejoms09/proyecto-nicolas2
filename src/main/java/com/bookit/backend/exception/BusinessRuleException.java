package com.bookit.backend.exception;

/**
 * Excepcion para reglas de negocio (estado invalido, transicion no permitida, etc.).
 */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
