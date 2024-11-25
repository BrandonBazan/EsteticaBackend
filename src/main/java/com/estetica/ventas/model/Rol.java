package com.estetica.ventas.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String nombre; // Ejemplo: "ADMIN", "CAJERO"

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Cajero> cajeros;

    // Getters y Setters
    public Long getId() {
        return rolId;
    }

    public void setId(Long id) {
        this.rolId = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cajero> getCajeros() {
        return cajeros;
    }

    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
    }
}

