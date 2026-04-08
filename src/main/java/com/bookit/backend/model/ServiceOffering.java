package com.bookit.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad de servicio disponible para agendar.
 */
@Entity
@Table(name = "services")
public class ServiceOffering {

    // ID tecnico autoincremental.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre visible del servicio.
    @Column(nullable = false, length = 120)
    private String name;

    // Descripcion corta para el cliente.
    @Column(nullable = false, length = 600)
    private String description;

    // Duracion estimada en minutos.
    @Column(nullable = false)
    private Integer durationMinutes;

    // Constructor requerido por JPA.
    protected ServiceOffering() {
    }

    // Constructor para crear servicios en semilla o administracion.
    public ServiceOffering(String name, String description, Integer durationMinutes) {
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
