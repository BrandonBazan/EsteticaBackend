package com.estetica.ventas.repository;

import com.estetica.ventas.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByFecha(LocalDate fecha); // Buscar gastos de un día específico
    
 // Método para obtener la suma de los montos de todos los pagos
    @Query("SELECT SUM(g.monto) FROM Gasto g")
    Double obtenerSumaGastos();
}

