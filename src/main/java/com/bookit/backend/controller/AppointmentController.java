package com.bookit.backend.controller;

import com.bookit.backend.dto.AppointmentCreateRequest;
import com.bookit.backend.dto.AppointmentResponse;
import com.bookit.backend.dto.AppointmentStatusRequest;
import com.bookit.backend.dto.ResponseMapper;
import com.bookit.backend.model.AppointmentStatus;
import com.bookit.backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Endpoints REST para gestion de citas.
 */
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Lista citas. Si llega ?status, filtra por estado.
     */
    @GetMapping("/appointments")
    public List<AppointmentResponse> getAppointments(@RequestParam(required = false) AppointmentStatus status) {
        return appointmentService.listAppointments(Optional.ofNullable(status)).stream()
                .map(ResponseMapper::toAppointmentResponse)
                .toList();
    }

    /**
     * Crea una cita con estado inicial PENDING.
     */
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentCreateRequest request) {
        AppointmentResponse response = ResponseMapper.toAppointmentResponse(appointmentService.createAppointment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza estado de una cita (ej: PENDING -> DONE).
     */
    @PatchMapping("/appointments/{id}/status")
    public AppointmentResponse updateAppointmentStatus(@PathVariable Long id,
                                                       @Valid @RequestBody AppointmentStatusRequest request) {
        return ResponseMapper.toAppointmentResponse(appointmentService.updateStatus(id, request.status()));
    }

    /**
     * Cancela una cita segun reglas de negocio.
     */
    @DeleteMapping("/appointments/{id}")
    public AppointmentResponse cancelAppointment(@PathVariable Long id) {
        return ResponseMapper.toAppointmentResponse(appointmentService.cancelAppointment(id));
    }
}
