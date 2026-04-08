package com.bookit.backend.dto;

import com.bookit.backend.model.Appointment;
import com.bookit.backend.model.ServiceOffering;

/**
 * Convierte entidades JPA en DTOs de respuesta.
 */
public final class ResponseMapper {

    private ResponseMapper() {
    }

    /**
     * Mapea una entidad de servicio al formato expuesto por API.
     */
    public static ServiceResponse toServiceResponse(ServiceOffering service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes()
        );
    }

    /**
     * Mapea una entidad de cita al formato expuesto por API.
     */
    public static AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getClientName(),
                appointment.getClientEmail(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                toServiceResponse(appointment.getService())
        );
    }
}
