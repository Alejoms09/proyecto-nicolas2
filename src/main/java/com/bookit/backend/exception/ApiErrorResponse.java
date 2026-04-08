package com.bookit.backend.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Estructura estandar para errores REST de la API.
 */
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
}
