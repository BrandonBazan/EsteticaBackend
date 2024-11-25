package com.estetica.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estetica.ventas.model.Trabajador;
import com.estetica.ventas.service.TrabajadorService;

@RestController
@RequestMapping("/trabajadores")
@CrossOrigin(origins = "http://localhost:3000") // Permitir solicitudes desde React
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping
    public List<Trabajador> obtenerTrabajadores() {
        return trabajadorService.listarTrabajadores();
    }

    @PostMapping
    public Trabajador crearTrabajador(@RequestBody Trabajador trabajador) {
        return trabajadorService.guardarTrabajador(trabajador);
    }

    @PutMapping("/{id}")
    public Trabajador actualizarTrabajador(@PathVariable Long id, @RequestBody Trabajador trabajador) {
        return trabajadorService.actualizarTrabajador(id, trabajador);
    }

    @DeleteMapping("/{id}")
    public void eliminarTrabajador(@PathVariable Long id) {
        trabajadorService.eliminarTrabajador(id);
    }
}

