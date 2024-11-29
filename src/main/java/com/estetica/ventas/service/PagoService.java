package com.estetica.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estetica.ventas.model.Pago;
import com.estetica.ventas.repository.PagoRepository;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerPagosPorTrabajador(Long trabajadorId) {
        return pagoRepository.findByTrabajadorId(trabajadorId);
    }

    public Pago crearPago(Pago pago) {
        return pagoRepository.save(pago);
    }
    
    public Double obtenerTotalPagos() {
        return pagoRepository.obtenerSumaPagos();
    }
    public Double obtenerTotalPagosEfectivo() {
        return pagoRepository.obtenerSumaMontoPorFormaPago("Efectivo");
    }
    public Double obtenerTotalPagosTransferencia() {
        return pagoRepository.obtenerSumaMontoPorFormaPago("Transferencia");
    }
}

