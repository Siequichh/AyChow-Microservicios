package com.utp.Aychow.FrontEnd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVenta {

    private Long idDetalleVenta;
    private Venta venta;
    private Long idProducto;
    private int cantidad;
    private float precioUnitario;
    private String direccionEntrega;
}