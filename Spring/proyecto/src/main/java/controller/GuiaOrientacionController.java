package controller;

import com.example.proyecto.Actividad;
import com.example.proyecto.ActividadRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/actividades")
public class GuiaOrientacionController {

    private final ActividadRepository actividadRepository;

    @Autowired
    public GuiaOrientacionController(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Operation(summary = "Obtiene los detalles de una actividad por su ID")
    @GetMapping("/{actividadId}")
    public ResponseEntity<Actividad> obtenerDetallesActividad(@PathVariable Long actividadId) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        return actividadOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Agrega una nueva actividad")
    @PostMapping("/")
    public ResponseEntity<Actividad> agregarActividad(@RequestBody Actividad actividad) {
        Actividad nuevaActividad = actividadRepository.save(actividad);
        return ResponseEntity.ok(nuevaActividad);
    }

    @Operation(summary = "Edita una actividad existente")
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

    @Operation(summary = "Elimina una actividad por su ID")
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
