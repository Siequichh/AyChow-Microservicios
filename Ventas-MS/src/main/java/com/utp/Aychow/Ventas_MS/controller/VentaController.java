package com.utp.Aychow.Ventas_MS.controller;

import com.utp.Aychow.Ventas_MS.entity.DetalleVenta;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.request.VentaRequest;
import com.utp.Aychow.Ventas_MS.service.ReportService;
import com.utp.Aychow.Ventas_MS.service.VentaService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    @Autowired
    private final VentaService ventaService;
    @Autowired
    private final ReportService reportService;

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

    @GetMapping("/pdf/{idVenta}")
    public ResponseEntity<byte[]> generarPdf(@PathVariable Long idVenta, @RequestParam Long idUsuario) {
        try {
            byte[] pdfBytes = reportService.exportToPdf(idUsuario, idVenta);

            if (pdfBytes.length == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=compra_" + idVenta + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

