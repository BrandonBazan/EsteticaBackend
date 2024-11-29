package com.estetica.ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estetica.ventas.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByTrabajadorId(Long trabajadorId);
    
 // Método para obtener la suma de los montos de todos los pagos
    @Query("SELECT SUM(p.monto) FROM Pago p")
    Double obtenerSumaPagos();
    
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.formaPago = :formaPago")
    Double obtenerSumaMontoPorFormaPago(@Param("formaPago") String formaPago);
}