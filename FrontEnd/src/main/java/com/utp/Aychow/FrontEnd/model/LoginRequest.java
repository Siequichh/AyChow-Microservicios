package com.utp.Aychow.FrontEnd.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class LoginRequest {
    private String correo;
    private String password;
}
