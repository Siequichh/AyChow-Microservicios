package com.utp.Aychow.FrontEnd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Venta {

    private Long idVenta;
    private Long idUsuario;
    private LocalDate fecha;
    private float total;
    private List<DetalleVenta> detalles;
}