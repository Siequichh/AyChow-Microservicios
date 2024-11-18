package com.utp.Aychow.Ventas_MS.request;

import lombok.Data;

@Data
public class DetalleVentaRequest {
    private Long idProducto;
    private Integer cantidad;
    private float precioUnitario;
    private String direccionEntrega;
}
