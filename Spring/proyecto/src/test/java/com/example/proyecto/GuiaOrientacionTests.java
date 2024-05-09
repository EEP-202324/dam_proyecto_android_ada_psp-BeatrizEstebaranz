package com.example.proyecto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.swagger.models.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GuiaOrientacionTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ActividadRepository actividadRepository;
	   @Test
	    public void agregarNuevaActividad_crearaActividadExitosamente() {
	        // Arrange
	        Actividad newActividad = new Actividad();

	        // Act
	        ResponseEntity<Actividad> response = restTemplate.postForEntity(
	                "http://localhost:" + port + "/actividades/", newActividad, Actividad.class);

	        // Assert
	        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	        assertThat(response.getBody()).isNotNull();
	        assertThat(response.getBody().getNombre()).isEqualTo(newActividad.getNombre());
	        assertThat(response.getBody().getDescripcion()).isEqualTo(newActividad.getDescripcion());
	        assertThat(response.getBody().getUniversidad()).isEqualTo(newActividad.getUniversidad());
	    }

	    @Test
	    public void eliminarActividadExistente_eliminaActividadExitosamente() {
	        // Arrange
	        Long actividadId = 1L;
	        Actividad existingActividad = new Actividad();
	        actividadRepository.save(existingActividad);

	        // Act
	        restTemplate.delete("http://localhost:" + port + "/actividades/" + actividadId);

	        // Assert
	        Optional<Actividad> result = actividadRepository.findById(actividadId);
	        assertThat(result).isNotPresent();
	    }

	    @Test
	    public void agregarOtraNuevaActividad_crearaActividadExitosamente() {
	        // Arrange
	        Actividad newActividad = new Actividad();

	        // Act
	        ResponseEntity<Actividad> response = restTemplate.postForEntity(
	                "http://localhost:" + port + "/actividades/", newActividad, Actividad.class);

	        // Assert
	        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	        assertThat(response.getBody()).isNotNull();
	        assertThat(response.getBody().getNombre()).isEqualTo(newActividad.getNombre());
	        assertThat(response.getBody().getDescripcion()).isEqualTo(newActividad.getDescripcion());
	        assertThat(response.getBody().getUniversidad()).isEqualTo(newActividad.getUniversidad());
	    }

	    @Test
	    public void eliminarOtraActividadExistente_eliminaActividadExitosamente() {
	        // Arrange
	        Long actividadId = 2L;
	        Actividad existingActividad = new Actividad();
	        actividadRepository.save(existingActividad);

	        // Act
	        restTemplate.delete("http://localhost:" + port + "/actividades/" + actividadId);

	        // Assert
	        Optional<Actividad> result = actividadRepository.findById(actividadId);
	        assertThat(result).isNotPresent();
	    }
	}