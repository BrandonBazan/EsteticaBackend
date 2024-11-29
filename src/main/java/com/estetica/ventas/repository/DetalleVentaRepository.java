package com.estetica.ventas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estetica.ventas.model.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
	List<DetalleVenta> findByVentaId(Long ventaId); 
	
	// Sumar las comisiones de los detalles de ventas de un día específico
	@Query("SELECT SUM(dv.comision) FROM DetalleVenta dv " +
		       "JOIN dv.venta v " +
		       "WHERE DATE(v.fecha) = :fecha")
		Double sumarComisionesPorFecha(@Param("fecha") LocalDate fecha);
    
    // Sumar el total de ventas de un día específico
    @Query("SELECT SUM(d.subtotal) FROM DetalleVenta d WHERE d.venta.fecha = :fecha")
    double sumarVentasPorFecha(@Param("fecha") LocalDate fecha);
    
    // Obtener el número total de ventas realizadas en un día específico
    @Query("SELECT COUNT(d) FROM DetalleVenta d WHERE d.venta.fecha = :fecha")
    int contarVentasPorFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT SUM(d.subtotal) FROM DetalleVenta d WHERE d.venta.fecha = :fecha AND d.trabajador.id = :trabajadorId")
    double sumarVentasPorTrabajadorYFecha(@Param("fecha") LocalDate fecha, @Param("trabajadorId") Long trabajadorId);

    //Mostrar Totales por trabajador
    @Query("SELECT t.nombre,COUNT(dv.id), SUM(dv.subtotal), SUM(dv.comision) " +
    	       "FROM DetalleVenta dv " +
    	       "LEFT JOIN dv.servicio s " +
    	       "LEFT JOIN dv.producto p " +
    	       "INNER JOIN dv.trabajador t " +
    	       "INNER JOIN dv.venta v " +
    	       "WHERE DATE(v.fecha) = CURRENT_DATE " +
    	       "GROUP BY t.nombre")
    	List<Object[]> obtenerResumenPorTrabajador();
    
    //Mostrar todos los Detalles
    	@Query(value = "SELECT " +
                "    dv.id, "+
                "    t.id, " +
                "    t.nombre AS trabajador_nombre, " +
                "    CASE " +
                "        WHEN p.nombre IS NOT NULL THEN p.nombre " +
                "        WHEN s.nombre IS NOT NULL THEN s.nombre " +
                "        ELSE dv.nombre_personalizado " +
                "    END AS nombre_producto_servicio, " +
                "    dv.subtotal, " +
                "    dv.comision " +
                "FROM detalle_venta dv " +
                "LEFT JOIN servicio s ON dv.servicio_id = s.id " +
                "LEFT JOIN producto p ON dv.producto_id = p.id " +
                "INNER JOIN trabajador t ON dv.trabajador_id = t.id " +
                "INNER JOIN venta v ON dv.venta_id = v.id " +
                "WHERE DATE(v.fecha) = CURRENT_DATE " +
                "ORDER BY dv.id", 
                nativeQuery = true)
    	List<Object[]> obtenerDetallesConNombreProductoOServicio();
	
}
