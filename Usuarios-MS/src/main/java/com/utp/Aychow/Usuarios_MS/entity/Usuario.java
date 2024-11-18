package com.utp.Aychow.Usuarios_MS.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
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

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    @JsonBackReference
    private Rol rol;
}