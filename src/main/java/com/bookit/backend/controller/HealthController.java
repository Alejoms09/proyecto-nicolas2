package com.bookit.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint de salud usado por healthchecks de Docker.
 */
@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Devuelve UP si la API puede hablar con base de datos.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        try {
            // Verificacion minima de conectividad SQL.
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return ResponseEntity.ok(Map.of("status", "UP"));
        } catch (Exception ex) {
            // Si falla DB o consulta, se reporta DOWN para orquestadores.
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("status", "DOWN"));
        }
    }
}
