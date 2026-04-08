package com.bookit.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Payload para crear una cita.
 */
public record AppointmentCreateRequest(
        // Nombre del cliente que agenda la cita.
        @NotBlank(message = "clientName is required")
        String clientName,
        // Correo de contacto del cliente.
        @NotBlank(message = "clientEmail is required")
        @Email(message = "clientEmail must be a valid email")
        String clientEmail,
        // Fecha del turno.
        @NotNull(message = "date is required")
        LocalDate date,
        // Hora del turno.
        @NotNull(message = "time is required")
        LocalTime time,
        // Servicio elegido por el cliente.
        @NotNull(message = "serviceId is required")
        Long serviceId
) {
}
