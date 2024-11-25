package com.estetica.ventas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estetica.ventas.Dto.DetalleVentaDTO;
import com.estetica.ventas.model.DetalleVenta;
import com.estetica.ventas.model.Producto;
import com.estetica.ventas.model.Servicio;
import com.estetica.ventas.model.Venta;
import com.estetica.ventas.repository.DetalleVentaRepository;
import com.estetica.ventas.repository.ProductoRepository;
import com.estetica.ventas.repository.ServicioRepository;

@Service
public class DetalleVentaService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> crearDetallesVenta(List<DetalleVentaDTO> detallesDTO, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaDTO detalleDTO : detallesDTO) {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVenta(venta);

            double subtotal = 0.0;

            // Si es un producto, obtener el precio y calcular el subtotal
            if (detalleDTO.getProductoId() != null) {
                Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                subtotal = producto.getPrecio() * detalleDTO.getCantidad();
                detalleVenta.setProducto(producto);
            }

            // Si es un servicio, obtener el precio y calcular el subtotal
            if (detalleDTO.getServicioId() != null) {
                Servicio servicio = servicioRepository.findById(detalleDTO.getServicioId())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

                subtotal = servicio.getPrecio() * detalleDTO.getCantidad();
                detalleVenta.setServicio(servicio);
            }

            // Asignar la cantidad y el subtotal calculado
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setSubtotal(subtotal);

            detalleVentaRepository.save(detalleVenta);
            detalles.add(detalleVenta);
        }

        return detalles;
    }
    
    public List<DetalleVenta> listarDetallesVenta() {
        return detalleVentaRepository.findAll();
    }

    public void eliminarDetalleVenta(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
