package com.utp.Aychow.Usuarios_MS.controller;

import com.utp.Aychow.Usuarios_MS.entity.Usuario;
import com.utp.Aychow.Usuarios_MS.request.LoginRequest;
import com.utp.Aychow.Usuarios_MS.request.UsuarioRequest;
import com.utp.Aychow.Usuarios_MS.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
    @Transactional
    public Usuario getUsuarioByEmail(@RequestParam String email) {
        return usuarioService.getUsuarioByCorreo(email);
    }

    @PostMapping("/login")
    @Transactional
    public Usuario login(@RequestBody LoginRequest loginRequest) {
            Usuario usuario= usuarioService.autenticarUsuario(loginRequest.getCorreo(), loginRequest.getPassword());
            if (usuario != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        usuario, null, Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().getNombreRol())));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        return usuario;
    }


    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario currentUser = (Usuario) authentication.getPrincipal();
            return ResponseEntity.ok(currentUser); }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}

