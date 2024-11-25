package com.estetica.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estetica.ventas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
