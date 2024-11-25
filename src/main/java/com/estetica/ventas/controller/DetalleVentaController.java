package com.estetica.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estetica.ventas.model.DetalleVenta;
import com.estetica.ventas.service.DetalleVentaService;

@RestController
@RequestMapping("/detalles-venta")
@CrossOrigin(origins = "http://localhost:3000")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVenta> obtenerDetallesVenta() {
        return detalleVentaService.listarDetallesVenta();
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalleVenta(@PathVariable Long id) {
        detalleVentaService.eliminarDetalleVenta(id);
    }
}

