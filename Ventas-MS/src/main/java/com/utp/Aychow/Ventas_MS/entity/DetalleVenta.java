package com.utp.Aychow.Ventas_MS.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private float precioUnitario;

    @Column(nullable = false)
    private String direccionEntrega;
}