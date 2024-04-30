package com.example.proyecto;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
	 // Método para buscar actividades por el nombre de la universidad
    List<Actividad> findByNombre(String nombre);
}
