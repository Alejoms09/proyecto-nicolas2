package com.bookit.backend.dto;

/**
 * Respuesta publica de un servicio.
 */
public record ServiceResponse(
        Long id,
        String name,
        String description,
        Integer durationMinutes
) {
}
