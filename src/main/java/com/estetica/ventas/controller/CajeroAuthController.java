package com.estetica.ventas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.estetica.ventas.model.Cajero;
import com.estetica.ventas.model.Rol;
import com.estetica.ventas.repository.CajeroRepository;
import com.estetica.ventas.repository.RolRepository;

@RestController
@RequestMapping("/api/cajeros")
public class CajeroAuthController {

    private final CajeroRepository cajeroRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public CajeroAuthController(CajeroRepository cajeroRepository, PasswordEncoder passwordEncoder,RolRepository rolRepository) {
        this.cajeroRepository = cajeroRepository;
        this.passwordEncoder = passwordEncoder;
		this.rolRepository = rolRepository;
    }


    @PostMapping("/register")
    public Cajero registerCajero(@RequestBody Map<String, Object> cajeroData) {
        String username = (String) cajeroData.get("username");
        String password = (String) cajeroData.get("password");
        String nombre = (String) cajeroData.get("nombre");
        String turno = (String) cajeroData.get("turno");
        String rolNombre = (String) cajeroData.get("rol"); // Obtener el rol desde el JSON

        // Validar que el nombre de usuario no esté en uso
        if (cajeroRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Buscar el rol en la base de datos
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        // Crear el nuevo cajero
        Cajero cajero = new Cajero();
        cajero.setUsername(username);
        cajero.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
        cajero.setNombre(nombre);
        cajero.setTurno(turno);
        cajero.setRoles(List.of(rol)); // Asignar el rol
        cajero.setActivo(true);

        // Guardar y devolver el cajero registrado
        return cajeroRepository.save(cajero);
    }



    @GetMapping("/me")
    public Cajero getCurrentCajero(@AuthenticationPrincipal UserDetails userDetails) {
        return cajeroRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Cajero no encontrado"));
    }
}
