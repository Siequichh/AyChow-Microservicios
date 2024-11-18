package com.utp.Aychow.Ventas_MS.dao;

import com.utp.Aychow.Ventas_MS.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VentaDAO extends JpaRepository<Venta, Long> {
    List<Venta> findByIdUsuario(Long idUsuario);

}
