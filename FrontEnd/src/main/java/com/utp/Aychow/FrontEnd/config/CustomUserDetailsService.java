package com.utp.Aychow.FrontEnd.config;

import com.utp.Aychow.FrontEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private WebClient.Builder webClientBuilder;


    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + correo)
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        return new CustomUserDetails(usuario);
    }
}