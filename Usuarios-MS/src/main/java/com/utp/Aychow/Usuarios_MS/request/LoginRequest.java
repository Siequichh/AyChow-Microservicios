package com.utp.Aychow.Usuarios_MS.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String password;
}
