package com.estetica.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estetica.ventas.model.Trabajador;
import com.estetica.ventas.repository.TrabajadorRepository;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    public List<Trabajador> listarTrabajadores() {
        return trabajadorRepository.findAll();
    }

    public Trabajador guardarTrabajador(Trabajador trabajador) {
        return trabajadorRepository.save(trabajador);
    }

    public Trabajador actualizarTrabajador(Long id, Trabajador trabajadorActualizado) {
        return trabajadorRepository.findById(id).map(trabajador -> {
            trabajador.setNombre(trabajadorActualizado.getNombre());
            trabajador.setPorcentajeComision(trabajadorActualizado.getPorcentajeComision());
            trabajador.setEspecialidad(trabajadorActualizado.getEspecialidad());
            return trabajadorRepository.save(trabajador);
        }).orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
    }

    public void eliminarTrabajador(Long id) {
        trabajadorRepository.deleteById(id);
    }
}
