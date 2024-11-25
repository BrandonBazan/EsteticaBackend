package com.estetica.ventas.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estetica.ventas.service.VentaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumenController {

    private static final Logger logger = LoggerFactory.getLogger(ResumenController.class);

    private final VentaService ventaService;

    public ResumenController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/resumen-dia")
    public ResponseEntity<Map<String, Object>> obtenerResumenDia() {
        try {
            logger.info("Iniciando cálculo del resumen del día");

            double totalVentas = ventaService.obtenerTotalVentasDia();
            logger.info("Total ventas del día: {}", totalVentas);

            double totalComisiones = ventaService.obtenerTotalComisionesDia();
            logger.info("Total comisiones del día: {}", totalComisiones);

            int numVentas = ventaService.obtenerNumVentasDia();
            logger.info("Número de ventas del día: {}", numVentas);

            Map<String, Object> resumen = new HashMap<>();
            resumen.put("totalVentas", totalVentas);
            resumen.put("totalComisiones", totalComisiones);
            resumen.put("numVentas", numVentas);

            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            logger.error("Error al obtener el resumen del día", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al obtener el resumen del día"));
        }
    }
    
    @GetMapping("/ventas-por-trabajador")
    public ResponseEntity<List<Map<String, Object>>> obtenerResumenPorTrabajador() {
        List<Map<String, Object>> resumen = ventaService.obtenerResumenPorTrabajador();
        System.out.println(resumen);
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/detalles-venta")
    public ResponseEntity<List<Map<String, Object>>> obtenerDetallesVenta() {
        List<Map<String, Object>> detalles = ventaService.obtenerDetallesConNombreProductoOServicio();
        return ResponseEntity.ok(detalles);
    }
    
    @GetMapping("/resumen-totales")
    public ResponseEntity<Map<String, Double>> obtenerResumenTotales() {
        Map<String, Double> resumen = ventaService.obtenerResumenTotales();
        return ResponseEntity.ok(resumen);
    }

}
