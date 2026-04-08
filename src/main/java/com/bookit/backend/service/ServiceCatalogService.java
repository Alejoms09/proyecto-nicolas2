package com.bookit.backend.service;

import com.bookit.backend.model.ServiceOffering;
import com.bookit.backend.repository.ServiceOfferingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Logica de negocio para consulta de servicios.
 */
@Service
public class ServiceCatalogService {

    private final ServiceOfferingRepository serviceRepository;

    public ServiceCatalogService(ServiceOfferingRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Retorna todos los servicios ordenados por nombre.
     */
    public List<ServiceOffering> getAllServices() {
        return serviceRepository.findAllByOrderByNameAsc();
    }
}
