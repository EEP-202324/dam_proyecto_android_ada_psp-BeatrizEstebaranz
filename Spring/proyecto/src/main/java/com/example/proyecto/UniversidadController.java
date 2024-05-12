package com.example.proyecto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/universidades")
public class UniversidadController {

    private final UniversidadRepository universidadRepository;

    @Autowired
    public UniversidadController(UniversidadRepository universidadRepository) {
        this.universidadRepository = universidadRepository;
    }

    @GetMapping
    public ResponseEntity<List<Universidad>> obtenerTodasUniversidades() {
        List<Universidad> universidades = (List<Universidad>) universidadRepository.findAll();
        return ResponseEntity.ok(universidades);
    }

    @GetMapping("/universidades/{id}")
    public ResponseEntity<String> obtenerNombreUniversidadPorId(@PathVariable Long id) {
        Optional<String> nombreUniversidadOptional = universidadRepository.findNombreUniversidadById_universidad(id);
        if (nombreUniversidadOptional.isPresent()) {
            String nombreUniversidad = nombreUniversidadOptional.get();
            return ResponseEntity.ok(nombreUniversidad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
