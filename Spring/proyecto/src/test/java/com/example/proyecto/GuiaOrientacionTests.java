package com.example.proyecto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class GuiaOrientacionTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActividadRepository actividadRepository;

    @Test
    public void obtenerDetallesActividad_DeberiaRetornarDetallesCorrectos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actividades/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Charla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value("Charla sobre cursos universitarios"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.universidad").value("Rey Juan Carlos III"));
    }

    @Test
    public void agregarActividad_DeberiaAgregarNuevaActividad() throws Exception {
        String nuevaActividadJson = "{\"id\": null, \"nombre\": \"Taller\", \"descripcion\": \"Taller de orientación vocacional\", \"universidad\": \"Universidad de Ejemplo\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/actividades/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevaActividadJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void shouldUpdateActivityDetailsWhenRequested() {
        // Crear una actividad nueva
        Actividad actividad = new Actividad(null, "Taller", "Taller de programación", "Universidad de Ejemplo");
        Actividad savedActividad = actividadRepository.save(actividad);

        // Modificar detalles de la actividad
        savedActividad.setNombre("Taller Actualizado");
        savedActividad.setDescripcion("Taller de programación actualizado");

        // Enviar solicitud PUT para actualizar la actividad
        ResponseEntity<Actividad> response = restTemplate.postForEntity("/actividades/{actividadId}", savedActividad, Actividad.class, savedActividad.getId());

        // Verificar que la solicitud fue exitosa y se devolvió la actividad actualizada
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedActividad);
    }

    @Test
    void shouldDeleteActivityWhenRequested() {
        // Crear una actividad nueva
        Actividad actividad = new Actividad(null, "Charla", "Charla sobre desarrollo personal", "Universidad de Ejemplo");
        Actividad savedActividad = actividadRepository.save(actividad);

        // Enviar solicitud DELETE para borrar la actividad
        restTemplate.delete("/actividades/{actividadId}", savedActividad.getId());

        // Verificar que la actividad se ha eliminado correctamente
        assertThat(actividadRepository.findById(savedActividad.getId())).isEmpty();
    }
}
