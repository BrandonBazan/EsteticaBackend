package com.estetica.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estetica.ventas.model.Pago;
import com.estetica.ventas.repository.PagoRepository;
import com.estetica.ventas.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/trabajador/{trabajadorId}")
    public ResponseEntity<List<Pago>> obtenerPagosPorTrabajador(@PathVariable String trabajadorId) {
        List<Pago> pagos;
        // Verifica si el trabajadorId es "todos" o "" (vacío)
        if (trabajadorId.equals("todos") || trabajadorId.equals("")) {
            pagos = pagoRepository.findAll(); // Obtener todos los pagos
        } else {
            pagos = pagoRepository.findByTrabajadorId(Long.parseLong(trabajadorId)); // Obtener pagos por trabajador específico
        }
        return ResponseEntity.ok(pagos);
    }


    @PostMapping
    public Pago crearPago(@RequestBody Pago pago) {
        return pagoService.crearPago(pago);
    }
    
    @GetMapping("/total-pagos")
    public Double obtenerTotalPagos() {
        return pagoService.obtenerTotalPagos();
    }
    
    @GetMapping("/total-pagos-efectivo")
    public Double obtenerTotalPagosEfectivo() {
        return pagoService.obtenerTotalPagosEfectivo();
    }
    
    @GetMapping("/total-pagos-transferencia")
    public Double obtenerTotalPagosTransferencia() {
        return pagoService.obtenerTotalPagosTransferencia();
    }
}

