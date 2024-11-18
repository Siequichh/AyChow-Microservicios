package com.utp.Aychow.Ventas_MS.request;

import com.utp.Aychow.Ventas_MS.entity.DetalleVenta;
import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {
    private Long idUsuario;
    private float total;
    private List<DetalleVenta> detalles;
}
