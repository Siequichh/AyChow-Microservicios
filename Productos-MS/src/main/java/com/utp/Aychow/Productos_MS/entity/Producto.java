package com.utp.Aychow.Productos_MS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private float precio;

    @Column(length = 500)
    private String detalle;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    @DecimalMin(value = "1.0")
    @DecimalMax(value = "10.0")
    private float rating;

}
