package com.bookit.backend.dto;

import com.bookit.backend.model.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Payload para actualizar estado de una cita.
 */
public record AppointmentStatusRequest(
        @NotNull(message = "status is required")
        AppointmentStatus status
) {
}
