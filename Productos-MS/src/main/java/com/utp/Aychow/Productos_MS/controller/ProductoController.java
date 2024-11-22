package com.utp.Aychow.Productos_MS.controller;

import com.utp.Aychow.Productos_MS.entity.Producto;
import com.utp.Aychow.Productos_MS.request.ProductoRequest;
import com.utp.Aychow.Productos_MS.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @GetMapping
    public Flux<Producto> listarProductos() {
        return Flux.fromIterable(productoService.getAllProductos());
    }


    @GetMapping("/rating-mayor/{rating}")
    public Flux<Producto> listarProductosConRatingMayorA(@PathVariable float rating) {
        return Flux.fromIterable(productoService.getProductosConRatingMayorA(rating));
    }


    @GetMapping("/rating-menor/{rating}")
    public Flux<Producto> listarProductosConRatingMenorOIgualA(@PathVariable float rating) {
        return Flux.fromIterable(productoService.getProductosConRatingMenorOIgualA(rating));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> obtenerProducto(@PathVariable Long id) {
        return Mono.justOrEmpty(productoService.getProductoById(id))
                .map(producto -> ResponseEntity.ok(producto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping(value="/nuevo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<Producto>> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("marca") String marca,
            @RequestParam("precio") float precio,
            @RequestParam("detalle") String detalle,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("rating") float rating,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {

        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre(nombre);
        productoRequest.setMarca(marca);
        productoRequest.setPrecio(precio);
        productoRequest.setDetalle(detalle);
        productoRequest.setCantidad(cantidad);
        productoRequest.setRating(rating);
        productoRequest.setImagen(imagen.getBytes());

        Producto producto = productoService.guardarProducto(productoRequest);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(producto));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> editarProducto(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("marca") String marca,
            @RequestParam("precio") float precio,
            @RequestParam("detalle") String detalle,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("rating") float rating,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre(nombre);
        productoRequest.setMarca(marca);
        productoRequest.setPrecio(precio);
        productoRequest.setDetalle(detalle);
        productoRequest.setCantidad(cantidad);
        productoRequest.setRating(rating);
        if (imagen != null && !imagen.isEmpty()) {
            productoRequest.setImagen(imagen.getBytes());
        }

        Producto productoActualizado = productoService.editarProducto(id, productoRequest);
        return Mono.just(ResponseEntity.ok(productoActualizado));
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return Mono.just(ResponseEntity.noContent().build());
    }


    @GetMapping("/nombre/{nombre}")
    public Flux<Producto> listarProductosPorNombre(@PathVariable String nombre) {
        return Flux.fromIterable(productoService.getProductosPorNombre(nombre));
    }


    @GetMapping("/marca/{marca}")
    public Flux<Producto> listarProductosPorMarca(@PathVariable String marca) {
        return Flux.fromIterable(productoService.getProductosPorMarca(marca));
    }


    @GetMapping("/imagen/{id}")
    public Mono<ResponseEntity<byte[]>> obtenerImagen(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        byte[] imagen = producto.getImagen();

        if (imagen == null || imagen.length == 0) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imagen));
    }


}
