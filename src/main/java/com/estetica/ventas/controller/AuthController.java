package com.estetica.ventas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.estetica.ventas.model.Cajero;
import com.estetica.ventas.service.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Extraer detalles del usuario desde el objeto Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Asumimos que `Cajero` implementa `UserDetails` y tiene un método para obtener el ID
        Long cajeroId = ((Cajero) userDetails).getCajeroId(); // Asegúrate de que `Cajero` tenga este método

        // Generar el token JWT
        String token = jwtTokenProvider.generateToken(userDetails, cajeroId);

        // Crear la respuesta con el token y detalles del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", userDetails.getUsername());
        response.put("cajeroId", cajeroId); // Opcional, incluir el ID en la respuesta

        return response;
    }


}
