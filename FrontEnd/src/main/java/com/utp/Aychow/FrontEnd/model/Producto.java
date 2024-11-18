package com.utp.Aychow.FrontEnd.model;

import lombok.Data;

@Data
public class Producto {
    private Long idProducto;
    private String nombre;
    private String marca;
    private float precio;
    private String detalle;
    private byte[] imagen;
    private int cantidad;
    private float rating;
}