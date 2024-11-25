package com.estetica.ventas.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estetica.ventas.repository.CajeroRepository;

@Service
public class CajeroDetailsService implements UserDetailsService {

    private final CajeroRepository cajeroRepository;

    public CajeroDetailsService(CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cajeroRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cajero no encontrado con username: " + username));
    }
}

