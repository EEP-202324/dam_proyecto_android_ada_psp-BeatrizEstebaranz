package com.example.proyecto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actividades")
public class GuiaOrientacionController {

    private final ActividadRepository actividadRepository;

    @Autowired
    public GuiaOrientacionController(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/{actividadId}")
    public ResponseEntity<Actividad> obtenerDetallesActividad(@PathVariable Long actividadId) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        return actividadOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Actividad> agregarActividad(@RequestBody Actividad actividad) {
        Actividad nuevaActividad = actividadRepository.save(actividad);
        return ResponseEntity.ok(nuevaActividad);
    }

    @PutMapping("/{actividadId}")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long actividadId, @RequestBody Actividad actividadActualizada) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        if (actividadOptional.isPresent()) {
            Actividad actividadExistente = actividadOptional.get();
            actividadExistente.setNombre(actividadActualizada.getNombre());
            actividadExistente.setDescripcion(actividadActualizada.getDescripcion());
            actividadExistente.setUniversidad(actividadActualizada.getUniversidad());
            Actividad actividadActualizadaEnBD = actividadRepository.save(actividadExistente);
            return ResponseEntity.ok(actividadActualizadaEnBD);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{actividadId}")
    public ResponseEntity<Void> borrarActividad(@PathVariable Long actividadId) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        if (actividadOptional.isPresent()) {
            actividadRepository.deleteById(actividadId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

