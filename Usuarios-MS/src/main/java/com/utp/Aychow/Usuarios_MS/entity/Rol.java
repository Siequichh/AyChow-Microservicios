package com.utp.Aychow.Usuarios_MS.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.utp.Aychow.Usuarios_MS.config.validation.RolNombre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @RolNombre
    @NotNull
    @Column(nullable = false, unique = true)
    private String nombreRol;

    @OneToMany(mappedBy = "rol")
    @JsonManagedReference
    private List<Usuario> usuarios;
}