package com.bookit.backend.exception;

/**
 * Excepcion para recursos inexistentes (404).
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
