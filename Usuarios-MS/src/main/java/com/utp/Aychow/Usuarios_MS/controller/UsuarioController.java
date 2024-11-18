package com.utp.Aychow.Usuarios_MS.controller;

import com.utp.Aychow.Usuarios_MS.entity.Usuario;
import com.utp.Aychow.Usuarios_MS.request.LoginRequest;
import com.utp.Aychow.Usuarios_MS.request.UsuarioRequest;
import com.utp.Aychow.Usuarios_MS.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return usuarioService.registrarUsuario(usuarioRequest);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) {
        return usuarioService.editarUsuario(id, usuarioRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/email")
    public Usuario getUsuarioByEmail(@RequestParam String email) {
        return usuarioService.getUsuarioByCorreo(email);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.autenticarUsuario(loginRequest.getCorreo(), loginRequest.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario currentUser = (Usuario) authentication.getPrincipal(); // Cast or retrieve user details
            logger.info("Usuario autenticado: {}", currentUser);
            return ResponseEntity.ok(currentUser); } logger.warn("Usuario no autenticado");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}

