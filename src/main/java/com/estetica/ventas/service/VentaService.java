package com.estetica.ventas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estetica.ventas.Dto.DetalleVentaDTO;
import com.estetica.ventas.Dto.VentaDTO;
import com.estetica.ventas.model.DetalleVenta;
import com.estetica.ventas.model.Producto;
import com.estetica.ventas.model.Servicio;
import com.estetica.ventas.model.Trabajador;
import com.estetica.ventas.model.Venta;
import com.estetica.ventas.model.Venta.TipoPago;
import com.estetica.ventas.repository.DetalleVentaRepository;
import com.estetica.ventas.repository.ProductoRepository;
import com.estetica.ventas.repository.ServicioRepository;
import com.estetica.ventas.repository.TrabajadorRepository;
import com.estetica.ventas.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta registrarVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        // Asignar el tipo de pago
        if (ventaDTO.getTipoPago() == null || ventaDTO.getTipoPago().isEmpty()) {
            throw new IllegalArgumentException("El tipo de pago es obligatorio.");
        }

        try {
            TipoPago tipoPago = TipoPago.valueOf(ventaDTO.getTipoPago().toUpperCase());
            venta.setTipoPago(tipoPago);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de pago inválido. Valores permitidos: EFECTIVO, TARJETA, TRANSFERENCIA, OTRO.");
        }

        // Asignar el trabajador que registró la venta
        Trabajador trabajadorRegistrador = trabajadorRepository.findById(ventaDTO.getTrabajadorId())
                .orElseThrow(() -> new IllegalArgumentException("Trabajador no encontrado con ID: " + ventaDTO.getTrabajadorId()));
        venta.setTrabajador(trabajadorRegistrador);

        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0.0;

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();

            if (detalleDTO.getProductoId() != null) {
                Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));
                detalle.setProducto(producto);
                detalle.setSubtotal(producto.getPrecio() * detalleDTO.getCantidad());
            } else if (detalleDTO.getServicioId() != null) {
                Servicio servicio = servicioRepository.findById(detalleDTO.getServicioId())
                        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + detalleDTO.getServicioId()));
                detalle.setServicio(servicio);
                detalle.setSubtotal(servicio.getPrecio() * detalleDTO.getCantidad());
            } else if (detalleDTO.getNombrePersonalizado() != null) {
                detalle.setNombrePersonalizado(detalleDTO.getNombrePersonalizado());
                detalle.setPrecioPersonalizado(detalleDTO.getPrecioPersonalizado());
                detalle.setSubtotal(detalleDTO.getPrecioPersonalizado() * detalleDTO.getCantidad());
                detalle.setComision(detalleDTO.getComisionPersonalizada());
            } else {
                throw new IllegalArgumentException("Detalle inválido: debe contener productoId, servicioId o nombrePersonalizado.");
            }

            detalle.setCantidad(detalleDTO.getCantidad());

            // Asignar el trabajador al detalle
            Trabajador trabajadorDetalle = trabajadorRepository.findById(detalleDTO.getTrabajadorId())
                    .orElseThrow(() -> new IllegalArgumentException("Trabajador no encontrado para el detalle con ID: " + detalleDTO.getTrabajadorId()));
            detalle.setTrabajador(trabajadorDetalle);

            // Calcular comisión
            double comision = 0;
            if (detalle.getServicio() != null) {
                String categoria = detalle.getServicio().getCategoria();
                String especialidad = detalle.getTrabajador().getEspecialidad();
                if ("Color".equalsIgnoreCase(categoria) && "Estilista2".equalsIgnoreCase(especialidad)) {
                    comision = (detalle.getSubtotal() - 110) * (trabajadorDetalle.getPorcentajeComision() / 100);
                } else {
                    comision = (detalle.getSubtotal() - 10) * (trabajadorDetalle.getPorcentajeComision() / 100);
                }
            } else if (detalle.getProducto() != null) {
                comision = detalle.getSubtotal() * 0.10;
            } else if (detalle.getNombrePersonalizado() != null) {
                if (detalleDTO.getComisionPersonalizada() != null) {
                    comision = detalleDTO.getComisionPersonalizada();
                } else {
                    comision = detalle.getSubtotal() * 0.10;
                }
            }
            detalle.setComision(comision);

            detalle.setVenta(venta);
            detalles.add(detalle);
            total += detalle.getSubtotal();
        }

        // Establecer el total calculado
        venta.setTotal(total);

        // Establecer la propina si está presente
        double propina = ventaDTO.getPropina() != null ? ventaDTO.getPropina() : 0.0;
        venta.setPropina(propina);

        // Guarda la venta y los detalles
        Venta ventaGuardada = ventaRepository.save(venta);
        detalleVentaRepository.saveAll(detalles);

        return ventaGuardada;
    }


    
    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
    
 // Obtener el total de ventas del día
    public double obtenerTotalVentasDia() {
        return ventaRepository.sumarVentasPorFecha(LocalDate.now());
    }

    // Obtener el total de comisiones del día
    public double obtenerTotalComisionesDia() {
        return detalleVentaRepository.sumarComisionesPorFecha(LocalDate.now());
    }

    // Obtener el número de ventas del día
    public int obtenerNumVentasDia() {
        return ventaRepository.countVentasPorFecha(LocalDate.now());
    }
    
    //Obtener todos los detalles
    public List<Map<String, Object>> obtenerDetallesConNombreProductoOServicio() {
        List<Object[]> resultados = detalleVentaRepository.obtenerDetallesConNombreProductoOServicio();
        List<Map<String, Object>> detalles = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> detalle = new HashMap<>();
            detalle.put("id", fila[0]);
            detalle.put("trabajadorNombre", fila[1]);
            detalle.put("nombreProductoServicio", fila[2]);
            detalle.put("subtotal", fila[3]);
            detalle.put("comision", fila[4]);
            detalles.add(detalle);
        }

        return detalles;
    }
    
    //Obtener resumen por trabajador
    public List<Map<String, Object>> obtenerResumenPorTrabajador() {
        List<Object[]> resultados = detalleVentaRepository.obtenerResumenPorTrabajador();
        List<Map<String, Object>> resumen = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("trabajador", fila[0]);
            datos.put("numVentas", fila[1]);
            datos.put("totalVentas", fila[2]);
            datos.put("totalComision", fila[3]);
            resumen.add(datos);
        }

        return resumen;
    }
    
    public Map<String, Double> obtenerResumenTotales() {
        Double totalEfectivo = ventaRepository.obtenerTotalVentasEfectivoHoy();
        Double totalTarjeta = ventaRepository.obtenerTotalVentasTarjetaHoy();
        Double totalPropinas = ventaRepository.sumPropinasHoy();

        // Manejar valores nulos
        totalEfectivo = totalEfectivo != null ? totalEfectivo : 0.0;
        totalTarjeta = totalTarjeta != null ? totalTarjeta : 0.0;
        totalPropinas = totalPropinas != null ? totalPropinas : 0.0;

        Map<String, Double> resumen = new HashMap<>();
        resumen.put("totalEfectivo", totalEfectivo - totalPropinas); // Propinas descontadas de efectivo
        resumen.put("totalTarjeta", totalTarjeta + totalPropinas);  // Propinas sumadas a tarjeta

        return resumen;
    }

    

    
    
    
    
//    public double obtenerTotalVentasDia() {
//        try {
//            // Aquí implementas la lógica para obtener el total de ventas del día
//            return ventaRepository.sumarVentasPorFecha(LocalDate.now());
//        } catch (Exception e) {
//            // Loggear el error para análisis
//            log.error("Error al obtener total de ventas del día", e);
//            throw new RuntimeException("Error al obtener total de ventas del día", e);
//        }
//    }
//
//    public double obtenerTotalComisionesDia() {
//        try {
//            // Lógica similar para las comisiones
//            return detalleVentaRepository.sumarComisionesPorFecha(LocalDate.now());
//        } catch (Exception e) {
//            log.error("Error al obtener total de comisiones del día", e);
//            throw new RuntimeException("Error al obtener total de comisiones del día", e);
//        }
//    }

//    public int obtenerNumVentasDia() {
//        try {
//            // Lógica para obtener número de ventas
//            return ventaRepository.countByFecha(LocalDate.now());
//        } catch (Exception e) {
//            log.error("Error al obtener número de ventas del día", e);
//            throw new RuntimeException("Error al obtener número de ventas del día", e);
//        }
//    }
}
