package com.example.proyecto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final UniversidadRepository universidadRepository; 

    @Autowired
    public ActividadService(ActividadRepository actividadRepository, UniversidadRepository universidadRepository) {
        this.actividadRepository = actividadRepository;
        this.universidadRepository = universidadRepository;
    }

    public ResponseEntity<Actividad> agregarActividad(Actividad actividad) {
        // Verificar si la universidad existe
        Optional<Universidad> universidadOptional = universidadRepository.findById(actividad.getUniversidad().getId_universidad());
        if (!universidadOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); // O devuelve un error adecuado
        }

        // Establecer la universidad en la actividad (puede que ya esté establecida, dependiendo de cómo se recibe la actividad)
        actividad.setUniversidad(universidadOptional.get());

        // Guardar la actividad en la base de datos
        Actividad nuevaActividad = actividadRepository.save(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaActividad);
    }
}


