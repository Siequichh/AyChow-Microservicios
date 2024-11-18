package com.utp.Aychow.Productos_MS.service;

import com.utp.Aychow.Productos_MS.dao.ProductoDAO;
import com.utp.Aychow.Productos_MS.entity.Producto;
import com.utp.Aychow.Productos_MS.request.ProductoRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;

    @Override
    @Transactional
    public List<Producto> getAllProductos() {
        return productoDAO.findAll();
    }

    @Override
    @Transactional
    public Producto guardarProducto(ProductoRequest productoRequest) {
        Producto producto = new Producto();
        producto.setNombre(productoRequest.getNombre());
        producto.setMarca(productoRequest.getMarca());
        producto.setPrecio(productoRequest.getPrecio());
        producto.setDetalle(productoRequest.getDetalle());
        producto.setCantidad(productoRequest.getCantidad());
        producto.setRating(productoRequest.getRating());
        producto.setImagen(productoRequest.getImagen());
        return productoDAO.save(producto);
    }

    @Override
    @Transactional
    public Producto editarProducto(Long id, ProductoRequest productoRequest) {
        Producto productoExistente = productoDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        productoExistente.setNombre(productoRequest.getNombre());
        productoExistente.setMarca(productoRequest.getMarca());
        productoExistente.setPrecio(productoRequest.getPrecio());
        productoExistente.setDetalle(productoRequest.getDetalle());
        productoExistente.setCantidad(productoRequest.getCantidad());
        productoExistente.setRating(productoRequest.getRating());
        if (productoRequest.getImagen() != null) {
            productoExistente.setImagen(productoRequest.getImagen());
        }
        return productoDAO.save(productoExistente);
    }

    @Override
    @Transactional
    public Producto getProductoById(Long id) {
        return productoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public void eliminarProducto(Long idProducto) {
        if (productoDAO.existsById(idProducto)) {
            productoDAO.deleteById(idProducto);
        } else {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }
    }

    @Override
    @Transactional
    public List<Producto> getProductosConRatingMayorA(float rating) {
        return productoDAO.findByRatingGreaterThanEqual(rating);
    }

    @Override
    @Transactional
    public List<Producto> getProductosConRatingMenorOIgualA(float rating) {
        return productoDAO.findByRatingLessThanEqual(rating);
    }

    @Override
    @Transactional
    public List<Producto> getProductosPorNombre(String nombre) {
        return productoDAO.findByNombre(nombre);
    }

    @Override
    @Transactional
    public List<Producto> getProductosPorMarca(String marca) {
        return productoDAO.findByMarca(marca);
    }
}