package com.utp.Aychow.Usuarios_MS.entity;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;


    private String dni;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    private String password;
    private LocalDate fechaDeRegistro;
    private String direccion;
    private String telefono;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
}