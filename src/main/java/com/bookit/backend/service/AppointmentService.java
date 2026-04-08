package com.bookit.backend.service;

import com.bookit.backend.dto.AppointmentCreateRequest;
import com.bookit.backend.exception.BusinessRuleException;
import com.bookit.backend.exception.NotFoundException;
import com.bookit.backend.model.Appointment;
import com.bookit.backend.model.AppointmentStatus;
import com.bookit.backend.model.ServiceOffering;
import com.bookit.backend.repository.AppointmentRepository;
import com.bookit.backend.repository.ServiceOfferingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Logica de negocio para crear, consultar y actualizar citas.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceOfferingRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ServiceOfferingRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    /**
     * Lista citas, opcionalmente filtradas por estado.
     */
    public List<Appointment> listAppointments(Optional<AppointmentStatus> status) {
        return status
                .map(appointmentRepository::findByStatusOrderByAppointmentDateAscAppointmentTimeAsc)
                .orElseGet(appointmentRepository::findAllByOrderByAppointmentDateAscAppointmentTimeAsc);
    }

    /**
     * Crea cita validando que el servicio exista y asignando estado inicial PENDING.
     */
    @Transactional
    public Appointment createAppointment(AppointmentCreateRequest request) {
        ServiceOffering service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new NotFoundException("Service not found"));

        Appointment appointment = new Appointment(
                request.clientName().trim(),
                request.clientEmail().trim(),
                request.date(),
                request.time(),
                AppointmentStatus.PENDING,
                service
        );
        return appointmentRepository.save(appointment);
    }

    /**
     * Actualiza el estado de una cita si la transicion es valida.
     */
    @Transactional
    public Appointment updateStatus(Long id, AppointmentStatus nextStatus) {
        Appointment appointment = getAppointment(id);
        validateStatusChange(appointment.getStatus(), nextStatus);
        appointment.setStatus(nextStatus);
        return appointment;
    }

    /**
     * Marca cita como CANCELLED (no elimina fisicamente el registro).
     */
    @Transactional
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = getAppointment(id);
        validateStatusChange(appointment.getStatus(), AppointmentStatus.CANCELLED);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointment;
    }

    // Busca cita por ID o lanza 404.
    private Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    // Reglas de negocio solicitadas para cambios de estado.
    private void validateStatusChange(AppointmentStatus current, AppointmentStatus next) {
        if (current == AppointmentStatus.CANCELLED && next == AppointmentStatus.DONE) {
            throw new BusinessRuleException("A cancelled appointment cannot be marked as attended");
        }
        if (current == AppointmentStatus.DONE && next == AppointmentStatus.CANCELLED) {
            throw new BusinessRuleException("A done appointment cannot be cancelled");
        }
    }
}
