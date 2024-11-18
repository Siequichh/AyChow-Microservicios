package com.utp.Aychow.FrontEnd.controller;


import com.utp.Aychow.FrontEnd.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoViewController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos")
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/por-marca")
    @ResponseBody
    public List<Producto> listarProductosPorMarca(@RequestParam("marca") String marca) {
        return webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos/marca/{marca}", marca)
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto() {
        return "nuevoProducto";
    }


    @PostMapping("/nuevo")
    public String crearProducto(@RequestParam("nombre") String nombre,
                                @RequestParam("marca") String marca,
                                @RequestParam("precio") float precio,
                                @RequestParam("detalle") String detalle,
                                @RequestParam("cantidad") int cantidad,
                                @RequestParam("rating") float rating,
                                @RequestParam("imagen") MultipartFile imagen) throws IOException {

        WebClient webClient = webClientBuilder.build();

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("nombre", nombre);
        builder.part("marca", marca);
        builder.part("precio", precio);
        builder.part("detalle", detalle);
        builder.part("cantidad", cantidad);
        builder.part("rating", rating);
        builder.part("imagen", imagen.getResource());

        webClient.post()
                .uri("http://api-gateway/api/productos")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(Producto.class)
                .block();

        return "redirect:/upload?exito";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
        model.addAttribute("producto", producto);
        return "actualizarProducto";
    }

    @PostMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("marca") String marca,
                                 @RequestParam("precio") float precio,
                                 @RequestParam("detalle") String detalle,
                                 @RequestParam("cantidad") int cantidad,
                                 @RequestParam("rating") float rating,
                                 @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setPrecio(precio);
        producto.setDetalle(detalle);
        producto.setCantidad(cantidad);
        producto.setRating(rating);
        if (imagen != null && !imagen.isEmpty()) {
            producto.setImagen(imagen.getBytes());
        }

        webClientBuilder.build()
                .put()
                .uri("http://api-gateway/api/productos/{id}", id)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(Mono.just(producto), Producto.class)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();

        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        webClientBuilder.build()
                .delete()
                .uri("http://api-gateway/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        return "redirect:/productos";
    }


    @GetMapping("/detalles/{id}")
    public String detallesProducto(@PathVariable Long id, Model model) {
        Producto producto = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
        model.addAttribute("producto", producto);
        return "detallesProducto";
    }
}
