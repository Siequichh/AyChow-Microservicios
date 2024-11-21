package com.utp.Aychow.FrontEnd.controller;

import com.utp.Aychow.FrontEnd.model.Producto;
import com.utp.Aychow.FrontEnd.model.Usuario;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final WebClient.Builder webClientBuilder;

    public HomeController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        List<Producto> mejoresProductos = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos/rating-mayor/8.0")
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();

        List<Producto> productosEnDescuento = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos/rating-menor/4.0")
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();

        model.addAttribute("mejoresProductos", mejoresProductos);
        model.addAttribute("productosEnDescuento", productosEnDescuento);

        if (principal != null) {

            Usuario usuario = webClientBuilder.build()
                    .get()
                    .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();
            model.addAttribute("userName", usuario.getNombre());
        }

        return "index";
    }

        @GetMapping("/checkout")
        public String checkout() {
            return "checkout";
        }


    @GetMapping("/upload")
    public String subida() {
        return "upload";
    }
    @GetMapping("/admin")
    public String productosAdmin(Model model) {
        List<Producto> productos = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/productos")
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();
        model.addAttribute("listaProductos", productos);
        return "productosAdmin";
    }

    @GetMapping("/tienda")
    public String tienda() {
        return "tienda";
    }


    @GetMapping("/favoritos")
    public String favoritos() {
        return "favoritos";
    }

    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/restaurarContraseña")
    public String restaurarContraseña() {
        return "restaurarContraseña";
    }
}