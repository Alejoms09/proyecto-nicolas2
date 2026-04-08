package com.bookit.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad de cita agendada por un cliente.
 */
@Entity
@Table(
        name = "appointments",
        indexes = {
                @Index(name = "idx_appointments_status", columnList = "status"),
                @Index(name = "idx_appointments_schedule", columnList = "appointment_date,appointment_time")
        }
)
public class Appointment {

    // ID tecnico autoincremental.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del cliente.
    @Column(nullable = false, length = 140)
    private String clientName;

    // Correo del cliente.
    @Column(nullable = false, length = 200)
    private String clientEmail;

    // Fecha del turno.
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    // Hora del turno.
    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    // Estado funcional de la cita.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;

    // Servicio asociado. EAGER para evitar problemas de serializacion al mapear respuesta.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering service;

    // Constructor requerido por JPA.
    protected Appointment() {
    }

    // Constructor usado por la logica de negocio al crear la cita.
    public Appointment(String clientName, String clientEmail, LocalDate appointmentDate, LocalTime appointmentTime,
                       AppointmentStatus status, ServiceOffering service) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public ServiceOffering getService() {
        return service;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
