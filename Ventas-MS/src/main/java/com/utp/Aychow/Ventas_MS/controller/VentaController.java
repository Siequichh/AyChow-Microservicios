package com.utp.Aychow.Ventas_MS.controller;

import com.utp.Aychow.Ventas_MS.entity.DetalleVenta;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.request.VentaRequest;
import com.utp.Aychow.Ventas_MS.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public Venta obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.getVentaById(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Venta> listarVentasPorIdUsuario(@PathVariable Long idUsuario) {
        return ventaService.getVentasByIdUsuario(idUsuario);
    }

//    @PostMapping
//    public Venta generarVenta(@RequestBody VentaRequest ventaRequest) {
//        return ventaService.generarVenta(ventaRequest);
//    }
    @PostMapping
    public ResponseEntity<Venta> generarVenta(@RequestBody VentaRequest ventaRequest) {
        Venta nuevaVenta = ventaService.generarVenta(ventaRequest);
        return ResponseEntity.status(201).body(nuevaVenta);
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }

    @GetMapping("/ventas/{idVenta}/detalles")
    public ResponseEntity<List<DetalleVenta>> obtenerDetallesPorVenta(@PathVariable Long idVenta) {
        List<DetalleVenta> detalles = ventaService.obtenerDetallesPorVenta(idVenta);
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/boleta/{id}")
    public ResponseEntity<byte[]> emitirBoletaPDF(@PathVariable Long id) {
        byte[] pdf = ventaService.emitirBoletaPDF(id); //aun faltaaa
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=boleta.pdf")
                .body(pdf);
    }
}

