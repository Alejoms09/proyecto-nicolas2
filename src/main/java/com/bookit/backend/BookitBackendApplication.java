package com.bookit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend BookIt.
 */
@SpringBootApplication
public class BookitBackendApplication {

    public static void main(String[] args) {
        // Arranca el contexto de Spring y todos los componentes de la API.
        SpringApplication.run(BookitBackendApplication.class, args);
    }

}
