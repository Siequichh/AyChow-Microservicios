package com.utp.Aychow.Usuarios_MS.service;

import com.utp.Aychow.Usuarios_MS.entity.Usuario;
import com.utp.Aychow.Usuarios_MS.request.UsuarioRequest;

import java.util.List;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Long id);
    List<Usuario> getUsuariosByNombre(String nombre);
    Usuario getUsuarioByCorreo(String correo);
    Usuario registrarUsuario(UsuarioRequest usuarioRequest);
    Usuario editarUsuario(Long id, UsuarioRequest usuarioRequest);
    void eliminarUsuario(Long id);
    Usuario autenticarUsuario(String correo, String password);
}
