package com.example.proyecto;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GuiaOrientacionJsonTest {

    @Autowired
    private JacksonTester<Actividad> json;

    @Test
    void guiaActividadesTest() throws IOException {
        // Creamos un objeto de ejemplo para la guía de actividades
        Actividad actividad = new Actividad(1L, "Charla", "Charla sobre cursos universitarios", "Rey Juan Carlos III");

        // Verificamos que la serialización del objeto sea igual al contenido del archivo esperado
        assertThat(json.write(actividad)).isEqualToJson("expected.json");

        // Verificamos que el JSON tenga la propiedad 'id'
        assertThat(json.write(actividad)).extractingJsonPathNumberValue("$.id").isEqualTo(1);

        // Verificamos que el JSON tenga la propiedad 'nombre'
        assertThat(json.write(actividad)).extractingJsonPathStringValue("$.nombre").isEqualTo("Charla");

        // Verificamos que el JSON tenga la propiedad 'descripcion'
        assertThat(json.write(actividad)).extractingJsonPathStringValue("$.descripcion").isEqualTo("Charla sobre cursos universitarios");

        // Verificamos que el JSON tenga la propiedad 'universidad'
        assertThat(json.write(actividad)).extractingJsonPathStringValue("$.universidad").isEqualTo("Rey Juan Carlos III");
    }
}
