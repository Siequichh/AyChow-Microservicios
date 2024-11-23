package com.utp.Aychow.Productos_MS.service;

import com.utp.Aychow.Productos_MS.entity.Producto;
import com.utp.Aychow.Productos_MS.request.ProductoRequest;

import java.util.List;

public interface ProductoService {
    List<Producto> getAllProductos();
    Producto guardarProducto(ProductoRequest productoRequest);
    Producto editarProducto(Long id, ProductoRequest productoRequest);
    Producto getProductoById(Long id);
    void eliminarProducto(Long idProducto);
    List<Producto> getProductosConRatingMayorA(float rating);
    List<Producto> getProductosConRatingMenorOIgualA(float rating);
    List<Producto> getProductosPorNombre(String nombre);
    List<Producto> getProductosPorMarca(String marca);
    void reducirStock(Long idProducto, int cantidad);
}
