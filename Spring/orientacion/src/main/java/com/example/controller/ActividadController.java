package com.example.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Actividad;
import com.example.repository.ActividadRepository;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

	private final ActividadRepository actividadRepository;

	@Autowired
	public ActividadController(ActividadRepository actividadRepository) {
		this.actividadRepository = actividadRepository;
	}

	// Endpoint para obtener todas las actividades
	@GetMapping
	public ResponseEntity<List<Actividad>> obtenerTodasLasActividades() {
		Iterable<Actividad> actividadesIterable = actividadRepository.findAll();
		List<Actividad> actividades = new ArrayList<>();
		actividadesIterable.forEach(actividades::add);

		return ResponseEntity.ok(actividades);
	}

	// Endpoint para obtener una actividad por su ID
	@GetMapping("/{actividadId}")
	public ResponseEntity<Actividad> obtenerActividadPorId(@PathVariable Long actividadId) {
		Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
		return actividadOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Endpoint para agregar una nueva actividad
	@PostMapping
	public ResponseEntity<Actividad> agregarActividad(@RequestBody Actividad nuevaActividad) {
		Actividad actividadGuardada = actividadRepository.save(nuevaActividad);
		return ResponseEntity.ok(actividadGuardada);
	}

	// Endpoint para editar una actividad existente por su ID
	@PutMapping("/{actividadId}")
	public ResponseEntity<Actividad> editarActividad(@PathVariable Long actividadId,
			@RequestBody Actividad actividadActualizada) {
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

	// Endpoint para eliminar una actividad por su ID
	@DeleteMapping("/{actividadId}")
	public ResponseEntity<Void> eliminarActividad(@PathVariable Long actividadId) {
		Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
		if (actividadOptional.isPresent()) {
			actividadRepository.deleteById(actividadId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
