package com.estetica.ventas.service;

import com.estetica.ventas.model.Gasto;
import com.estetica.ventas.repository.GastoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;

    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public List<Gasto> obtenerGastosPorDia(LocalDate fecha) {
        return gastoRepository.findByFecha(fecha);
    }

    public Gasto registrarGasto(Gasto gasto) {
        gasto.setFecha();
        return gastoRepository.save(gasto);
    }

    public void eliminarGasto(Long id) {
        gastoRepository.deleteById(id);
    }
    
    public Double obtenerTotalGastos() {
    	return gastoRepository.obtenerSumaGastos();
    }
}
