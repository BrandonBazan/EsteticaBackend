package com.estetica.ventas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estetica.ventas.model.Cajero;

public interface CajeroRepository extends JpaRepository<Cajero, Long> {
	Optional<Cajero> findByUsername(String username);

}
