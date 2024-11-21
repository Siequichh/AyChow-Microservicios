package com.utp.Aychow.FrontEnd.model;

import lombok.Data;
import java.util.List;
@Data
public class Rol {
    private Long idRol;
    private String nombreRol;
    private List<Usuario> usuarios;
}
