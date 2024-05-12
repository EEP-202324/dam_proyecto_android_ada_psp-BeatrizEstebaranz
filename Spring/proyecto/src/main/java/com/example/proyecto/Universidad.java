package com.example.proyecto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "universidad")
public class Universidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_universidad;
    private String nombre_universidad;

    // Constructor vacío
    public Universidad() {
    }

    // Constructor con todos los atributos
    public Universidad(Long id_universidad, String nombre_universidad) {
        this.id_universidad = id_universidad;
        this.nombre_universidad = nombre_universidad;
    }

    // Getters y setters
    public Long getId_universidad() {
        return id_universidad;
    }

    public void setId_universidad(Long id_universidad) {
        this.id_universidad = id_universidad;
    }

    public String getNombre_universidad() {
        return nombre_universidad;
    }

    public void setNombre_universidad(String nombre_universidad) {
        this.nombre_universidad = nombre_universidad;
    }
}

