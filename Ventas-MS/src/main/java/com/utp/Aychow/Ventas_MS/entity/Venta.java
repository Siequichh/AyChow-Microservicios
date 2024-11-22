package com.utp.Aychow.Ventas_MS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private float total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleVenta> detalles;
}