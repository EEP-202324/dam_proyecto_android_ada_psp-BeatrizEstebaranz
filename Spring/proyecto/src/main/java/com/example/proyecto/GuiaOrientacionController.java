package com.example.proyecto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<Page<Actividad>> obtenerActividadesPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Actividad> actividades = actividadRepository.findAll(pageable);
        return ResponseEntity.ok(actividades);
    }

    @PostMapping("/")
    public ResponseEntity<Actividad> agregarActividad(@RequestBody Actividad actividad) {
        Actividad nuevaActividad = actividadRepository.save(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaActividad);
    }

    @PutMapping("/{actividadId}")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long actividadId, @RequestBody Actividad actividadActualizada) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        if (actividadOptional.isPresent()) {
            Actividad actividadExistente = actividadOptional.get();
            actividadExistente.setNombre(actividadActualizada.getNombre());//actualizan los atributos de la actividad existente con los valores de la actividad actualizada
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

