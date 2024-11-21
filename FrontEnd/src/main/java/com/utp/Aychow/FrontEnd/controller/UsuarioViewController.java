package com.utp.Aychow.FrontEnd.controller;


import com.utp.Aychow.FrontEnd.model.LoginRequest;
import com.utp.Aychow.FrontEnd.model.Usuario;
import com.utp.Aychow.FrontEnd.model.UsuarioRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping
    public String getAllUsuarios(Model model) {
        List<Usuario> usuarios = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/usuarios")
                .retrieve()
                .bodyToFlux(Usuario.class)
                .collectList()
                .block();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listaUsuarios"; // no esta la vista
    }


    @GetMapping("/{id}")
    public String getUsuarioById(@PathVariable Long id, Model model) {
        Usuario usuario = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/usuarios/{id}", id)
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();
        model.addAttribute("usuario", usuario);
        return "usuarios/detalleUsuario";
    }



    @PostMapping("/registro")
    public Mono<String> createUsuario(@ModelAttribute UsuarioRequest usuarioRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://api-gateway/api/usuarios")
                .bodyValue(usuarioRequest)
                .retrieve()
                .bodyToMono(Usuario.class)
                .flatMap(savedUser -> {
                    return Mono.just("redirect:/login");
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    return Mono.just("redirect:/registro?error");
                });
        }


    @PostMapping("/{id}")
    public String updateUsuario(@PathVariable Long id, @ModelAttribute UsuarioRequest usuarioRequest) {
        webClientBuilder.build()
                .put()
                .uri("http://api-gateway/api/usuarios/{id}", id)
                .bodyValue(usuarioRequest)
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();
        return "redirect:/usuarios";
    }


    @PostMapping("/eliminar/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        webClientBuilder.build()
                .delete()
                .uri("http://api-gateway/api/usuarios/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
        return "redirect:/usuarios"; //no esta la vista
    }



}
