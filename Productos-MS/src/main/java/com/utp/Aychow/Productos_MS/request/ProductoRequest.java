package com.utp.Aychow.Productos_MS.request;

import lombok.Data;

@Data
public class ProductoRequest {

    private String nombre;
    private String marca;
    private float precio;
    private String detalle;
    private byte[] imagen;
    private int cantidad;
    private float rating;

}