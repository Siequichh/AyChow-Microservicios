package com.utp.Aychow.Ventas_MS.service;

import com.utp.Aychow.Ventas_MS.entity.DetalleVenta;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.request.VentaRequest;

import java.util.List;

public interface VentaService {
    List<Venta> getAllVentas();
    Venta getVentaById(Long id);
    List<Venta> getVentasByIdUsuario(Long idUsuario);
    Venta generarVenta(VentaRequest ventaRequest);
    void eliminarVenta(Long id);
    byte[] emitirBoletaPDF(Long idVenta);
    List<DetalleVenta> obtenerDetallesPorVenta(Long idVenta);
}
