package com.bookit.backend.repository;

import com.bookit.backend.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Acceso a datos del catalogo de servicios.
 */
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    // Devuelve catalogo ordenado alfabeticamente.
    List<ServiceOffering> findAllByOrderByNameAsc();
}
