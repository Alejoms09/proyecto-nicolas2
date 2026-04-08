package com.bookit.backend.config;

import com.bookit.backend.model.ServiceOffering;
import com.bookit.backend.repository.ServiceOfferingRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Carga servicios base al arrancar la aplicacion si la tabla esta vacia.
 */
@Component
public class ServiceSeeder implements ApplicationRunner {

    private final ServiceOfferingRepository serviceRepository;

    public ServiceSeeder(ServiceOfferingRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Evita duplicar datos cuando la aplicacion se reinicia.
        if (serviceRepository.count() > 0) {
            return;
        }

        // Catalogo inicial para que el frontend pueda operar desde el primer inicio.
        List<ServiceOffering> services = List.of(
                new ServiceOffering("Corte de cabello", "Corte profesional con asesoria de estilo.", 45),
                new ServiceOffering("Asesoria academica", "Orientacion personalizada para plan de estudios y objetivos.", 60),
                new ServiceOffering("Consulta tecnica", "Diagnostico y recomendacion para problemas tecnicos.", 50),
                new ServiceOffering("Tutoria", "Sesion guiada para resolver dudas especificas.", 60)
        );

        // Persiste el catalogo semilla.
        serviceRepository.saveAll(services);
    }
}
