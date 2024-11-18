package com.utp.Aychow.Usuarios_MS.request;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String direccion;
    private String telefono;
    private Long idRol;
}
