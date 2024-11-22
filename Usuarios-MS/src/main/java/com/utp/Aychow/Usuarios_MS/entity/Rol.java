package com.utp.Aychow.Usuarios_MS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"usuarios"})
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nombreRol;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rol")
    @JsonIgnore
    private List<Usuario> usuarios;
}