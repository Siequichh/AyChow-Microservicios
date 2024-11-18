//package com.utp.Aychow.Usuarios_MS.service;
//
//import com.utp.Aychow.Usuarios_MS.dao.UsuarioDAO;
//import com.utp.Aychow.Usuarios_MS.entity.Usuario;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UsuarioDAO usuarioDAO;
//
//    @Override
//    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
//        Usuario usuario = usuarioDAO.findByCorreo(correo);
//        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol())));
//    }
//}

