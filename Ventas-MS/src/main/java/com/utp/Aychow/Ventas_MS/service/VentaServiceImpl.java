package com.utp.Aychow.Ventas_MS.service;


import com.utp.Aychow.Ventas_MS.dao.DetalleVentaDAO;
import com.utp.Aychow.Ventas_MS.dao.VentaDAO;
import com.utp.Aychow.Ventas_MS.entity.DetalleVenta;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.request.DetalleVentaRequest;
import com.utp.Aychow.Ventas_MS.request.VentaRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private final WebClient.Builder webClientBuilder;

    public VentaServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Override
    public List<Venta> getAllVentas() {
        return ventaDAO.findAll();
    }

    @Override
    public Venta getVentaById(Long idVenta) throws EntityNotFoundException {
        return ventaDAO.findById(idVenta).orElse(null);
    }

    @Override
    public List<Venta> getVentasByIdUsuario(Long idUsuario) {
        return ventaDAO.findByIdUsuario(idUsuario);
    }

@Override
@Transactional
public Venta generarVenta(VentaRequest ventaRequest) {
    Venta venta = new Venta();
    venta.setIdUsuario(ventaRequest.getIdUsuario());
    venta.setFecha(LocalDate.now());
    venta.setTotal(ventaRequest.getDetalles().stream()
            .map(det -> det.getCantidad() * det.getPrecioUnitario())
            .reduce(0.0f, Float::sum));

    Venta savedVenta = ventaDAO.save(venta);

    List<DetalleVenta> detalles = ventaRequest.getDetalles().stream().map(detReq -> {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(savedVenta);
        detalle.setIdProducto(detReq.getIdProducto());
        detalle.setCantidad(detReq.getCantidad());
        detalle.setPrecioUnitario(detReq.getPrecioUnitario());
        detalle.setDireccionEntrega(detReq.getDireccionEntrega() != null ? detReq.getDireccionEntrega() : "Dirección no proporcionada");

        // Reducir el stock
        try {
            webClientBuilder.build()
                    .patch()
                    .uri("http://api-gateway/api/productos/reducir-stock")
                    .bodyValue(Map.of(
                            "idProducto", detReq.getIdProducto(),
                            "cantidad", detReq.getCantidad()
                    ))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Fallo al comunicar con el servicio de productos", e);
        }


        return detalle;
    }).collect(Collectors.toList());

    detalleVentaDAO.saveAll(detalles);

    return savedVenta;
}






    @Override
    @Transactional
    public void eliminarVenta(Long id) {
        if (ventaDAO.existsById(id)) {
            ventaDAO.deleteById(id);
        } else {
            throw new EntityNotFoundException("Venta no encontrada con ID: " + id);
        }
    }

    public List<DetalleVenta> obtenerDetallesPorVenta(Long idVenta) {
        Venta venta = ventaDAO.findById(idVenta).orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
        return detalleVentaDAO.findByVenta(venta);
    }

        @Override
        public byte[] emitirBoletaPDF (Long idVenta){
            //faltaaa
            return new byte[0];
        }

    public Venta obtenerUltimaVentaPorUsuario(Long idUsuario) {
        return ventaDAO.findTopByIdUsuarioOrderByIdVentaDesc(idUsuario);
    }

}
