package com.utp.Aychow.Usuarios_MS.service;

import com.utp.Aychow.Usuarios_MS.dao.RolDAO;
import com.utp.Aychow.Usuarios_MS.dao.UsuarioDAO;
import com.utp.Aychow.Usuarios_MS.entity.Rol;
import com.utp.Aychow.Usuarios_MS.entity.Usuario;
import com.utp.Aychow.Usuarios_MS.request.UsuarioRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RolDAO rolDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.findAll();
    }

    @Override
    @Transactional
    public Usuario getUsuarioById(Long id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<Usuario> getUsuariosByNombre(String nombre) {
        return usuarioDAO.findByNombre(nombre);
    }
    @Override
    @Transactional
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioDAO.findByCorreo(correo);
    }

    @Override
    @Transactional  //este es para usuario no admin
    public Usuario registrarUsuario(UsuarioRequest usuarioRequest) {
        if (usuarioRequest.getDni() != null && usuarioDAO.existsByDni(usuarioRequest.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        Usuario usuario = new Usuario();

        usuario.setDni(usuarioRequest.getDni());
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setApellido(usuarioRequest.getApellido());
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        usuario.setFechaDeRegistro(LocalDate.now());
        usuario.setDireccion(usuarioRequest.getDireccion());
        usuario.setTelefono(usuarioRequest.getTelefono());
        usuario.setRol(rolDAO.findById(2L).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        return usuarioDAO.save(usuario);
    }


    @Override
    @Transactional
    public Usuario editarUsuario(Long id, UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioDAO.findById(id).orElse(null);
        if (usuario != null) {
            if (usuarioRequest.getDni() != null) {
                usuario.setDni(usuarioRequest.getDni());
            }
            if (usuarioRequest.getNombre() != null) {
                usuario.setNombre(usuarioRequest.getNombre());
            }
            if (usuarioRequest.getApellido() != null) {
                usuario.setApellido(usuarioRequest.getApellido());
            }
            if (usuarioRequest.getCorreo() != null) {
                usuario.setCorreo(usuarioRequest.getCorreo());
            }
            if (usuarioRequest.getPassword() != null) {
                usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
            }
            if (usuarioRequest.getDireccion() != null) {
                usuario.setDireccion(usuarioRequest.getDireccion());
            }
            if (usuarioRequest.getTelefono() != null) {
                usuario.setTelefono(usuarioRequest.getTelefono());
            }
            if (usuarioRequest.getIdRol() != null) {
                usuario.setRol(rolDAO.findById(usuarioRequest.getIdRol()).orElse(null));
            }
            return usuarioDAO.save(usuario);
        }
        return null;
    }



    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        if (usuarioDAO.existsById(id)) {
            usuarioDAO.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    @Transactional
    public Usuario autenticarUsuario(String correo, String password) {
        Usuario usuario = usuarioDAO.findByCorreo(correo);
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario;
        }
        throw new RuntimeException("Correo o contraseña incorrectos");
    }

    @Override
    public  List<Usuario> getUsuariosByRol(Rol idRol) {
        return usuarioDAO.findByRol(idRol);
    }


}