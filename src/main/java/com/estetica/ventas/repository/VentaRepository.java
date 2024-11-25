package com.estetica.ventas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estetica.ventas.model.Venta;
import com.estetica.ventas.model.Venta.TipoPago;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
	
	List<Venta> findByTipoPago(TipoPago tipoPago);
	
	// Sumar las ventas realizadas en el día
	@Query("SELECT SUM(v.total) FROM Venta v WHERE DATE(v.fecha) = :fecha")
	Double sumarVentasPorFecha(@Param("fecha") LocalDate fecha);


    // Sumar las comisiones realizadas en el día
    @Query("SELECT SUM(d.comision) FROM DetalleVenta d WHERE d.venta.fecha = :fecha")
    Double sumarComisionesPorFecha(@Param("fecha") LocalDate fecha);


    // Contar el número de ventas realizadas en el día
    @Query("SELECT COUNT(v.id) FROM Venta v WHERE DATE(v.fecha) = :fecha")
    int countVentasPorFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.tipoPago = 'Efectivo' AND DATE(v.fecha) = CURRENT_DATE")
    Double obtenerTotalVentasEfectivoHoy();

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.tipoPago != 'Efectivo' AND DATE(v.fecha) = CURRENT_DATE")
    Double obtenerTotalVentasTarjetaHoy();

    @Query("SELECT SUM(v.propina) FROM Venta v WHERE DATE(v.fecha) = CURRENT_DATE")
    Double sumPropinasHoy();
}
