package com.utp.Aychow.FrontEnd.model;

import lombok.Data;

@Data
public class Usuario {
    private Long idUsuario;
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String direccion;
    private String telefono;
    private int idRol;
}
