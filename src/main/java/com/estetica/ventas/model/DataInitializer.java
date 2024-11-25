package com.estetica.ventas.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.estetica.ventas.repository.RolRepository;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            // Verificar si no existe el rol "ADMIN"
            if (rolRepository.findByNombre("ADMIN") == null) {
                Rol adminRole = new Rol();
                adminRole.setNombre("ADMIN");
                rolRepository.save(adminRole);
            }

            // Verificar si no existe el rol "CAJERO"
            if (rolRepository.findByNombre("CAJERO") == null) {
                Rol cajeroRole = new Rol();
                cajeroRole.setNombre("CAJERO");
                rolRepository.save(cajeroRole);
            }
        };
    }
}
