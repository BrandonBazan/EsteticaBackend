package com.estetica.ventas.repository;

import com.estetica.ventas.model.Rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Puedes agregar métodos personalizados si es necesario.
    // Por ejemplo, si quieres buscar un rol por su nombre:
    Optional<Rol> findByNombre(String nombre);
}
