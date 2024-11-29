package com.estetica.ventas.controller;

import com.estetica.ventas.model.Gasto;
import com.estetica.ventas.service.GastoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final GastoService gastoService;

    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping("/dia")
    public ResponseEntity<List<Gasto>> obtenerGastosDelDia() {
        LocalDate hoy = LocalDate.now();
        List<Gasto> gastos = gastoService.obtenerGastosPorDia(hoy);
        return ResponseEntity.ok(gastos);
    }

    @PostMapping
    public ResponseEntity<Gasto> registrarGasto(@RequestBody Gasto gasto) {
        Gasto nuevoGasto = gastoService.registrarGasto(gasto);
        return ResponseEntity.ok(nuevoGasto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/total-gastos")
    public Double obtenerTotalGastos() {
        return gastoService.obtenerTotalGastos();
    }
}
