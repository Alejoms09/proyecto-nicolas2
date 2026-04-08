package com.bookit.backend.dto;

import com.bookit.backend.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Respuesta publica de una cita para el frontend.
 */
public record AppointmentResponse(
        Long id,
        String clientName,
        String clientEmail,
        LocalDate date,
        LocalTime time,
        AppointmentStatus status,
        ServiceResponse service
) {
}
