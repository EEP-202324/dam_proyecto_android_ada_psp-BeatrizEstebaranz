package com.example.proyecto;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //indica que maneja solicitudes REST
@RequestMapping("/actividades") //establece la ruta base para todas las solicitudes que se gestionarán en este controlador.
public class ActividadController {

	private final ActividadRepository actividadRepository;
	private final ActividadService actividadService;

	@Autowired
	public ActividadController(ActividadRepository actividadRepository, ActividadService actividadService) {
		this.actividadRepository = actividadRepository;
		this.actividadService = actividadService;
	}

	@GetMapping("/{actividadId}")
	public ResponseEntity<Actividad> obtenerDetallesActividad(@PathVariable Long actividadId) {
		Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
		actividadOptional.get().getUniversidad();
		return actividadOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Actividad>> obtenerActividades() {
	    List<Actividad> actividades = actividadRepository.findAll();
	    return ResponseEntity.ok(actividades);
	}

	@PostMapping("/")
	public ResponseEntity<Actividad> agregarActividad(@RequestBody Actividad actividad) {
		return actividadService.agregarActividad(actividad);
	}

	@PutMapping("/{actividadId}")
	public ResponseEntity<Actividad> editarActividad(@PathVariable Long actividadId,
			@RequestBody Actividad actividadActualizada) {
		Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
		if (actividadOptional.isPresent()) {
			Actividad actividadExistente = actividadOptional.get();
			actividadExistente.setNombre(actividadActualizada.getNombre());// actualizan los atributos de la actividad
																			// existente con los valores de la actividad
																			// actualizada
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
