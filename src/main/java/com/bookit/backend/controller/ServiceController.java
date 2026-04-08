package com.bookit.backend.controller;

import com.bookit.backend.dto.ResponseMapper;
import com.bookit.backend.dto.ServiceResponse;
import com.bookit.backend.service.ServiceCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST para consulta de servicios disponibles.
 */
@RestController
public class ServiceController {

    private final ServiceCatalogService serviceCatalogService;

    public ServiceController(ServiceCatalogService serviceCatalogService) {
        this.serviceCatalogService = serviceCatalogService;
    }

    /**
     * Retorna el catalogo de servicios ordenado por nombre.
     */
    @GetMapping("/services")
    public List<ServiceResponse> getServices() {
        return serviceCatalogService.getAllServices().stream()
                .map(ResponseMapper::toServiceResponse)
                .toList();
    }
}
