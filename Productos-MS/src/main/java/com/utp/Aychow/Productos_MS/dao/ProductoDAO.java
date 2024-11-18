package com.utp.Aychow.Productos_MS.dao;

import com.utp.Aychow.Productos_MS.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long> {

    List<Producto> findByRatingGreaterThanEqual(float rating);
    List<Producto> findByRatingLessThanEqual(float rating);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marca);
}
