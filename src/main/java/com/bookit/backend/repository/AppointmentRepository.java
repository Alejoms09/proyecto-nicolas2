package com.bookit.backend.repository;

import com.bookit.backend.model.Appointment;
import com.bookit.backend.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Acceso a datos de citas.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Devuelve todas las citas ordenadas por fecha y hora.
    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();

    // Devuelve citas filtradas por estado con el mismo orden cronologico.
    List<Appointment> findByStatusOrderByAppointmentDateAscAppointmentTimeAsc(AppointmentStatus status);
}
