package com.utp.Aychow.Usuarios_MS.dao;
import com.utp.Aychow.Usuarios_MS.entity.Rol;
import com.utp.Aychow.Usuarios_MS.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNombre(String nombre);
    Usuario findByCorreo(String correo);
    boolean existsByDni(String dni);
   List<Usuario> findByRol(Rol idRrol);
}
