package com.example.proyecto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UniversidadRepository extends JpaRepository<Universidad, Long> {
	 @Query("SELECT u.nombre_universidad FROM Universidad u WHERE u.id_universidad = :id")
	    Optional<String> findNombreUniversidadById_universidad(@Param("id") Long id);
}
