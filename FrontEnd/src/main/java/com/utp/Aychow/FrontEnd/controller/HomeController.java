package com.utp.Aychow.FrontEnd.controller;

import com.utp.Aychow.FrontEnd.model.Producto;
import com.utp.Aychow.FrontEnd.model.Usuario;
import com.utp.Aychow.FrontEnd.model.UsuarioRequest;
import com.utp.Aychow.FrontEnd.model.Venta;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.standard.PrinterInfo;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final WebClient.Builder webClientBuilder;

    public HomeController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/")
    public String index(Model model) {
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
        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        Usuario usuario = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();

        Long idUsuario = usuario.getIdUsuario();

        model.addAttribute("usuarioId", idUsuario);

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

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Usuario usuario, Principal principal, RedirectAttributes redirectAttributes) {

        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "El correo es un campo obligatorio.");
            return "redirect:/profile";
        }

        if (usuario.getDni() != null && !usuario.getDni().matches("\\d+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "El DNI debe contener solo números.");
            return "redirect:/profile";
        }

        Usuario existingUsuario = webClientBuilder
                .build()
                .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();

        if (existingUsuario != null) {
            UsuarioRequest usuarioRequest = new UsuarioRequest();
            usuarioRequest.setDni(usuario.getDni());
            usuarioRequest.setNombre(usuario.getNombre());
            usuarioRequest.setApellido(usuario.getApellido());
            usuarioRequest.setCorreo(usuario.getCorreo());
            usuarioRequest.setDireccion(usuario.getDireccion());
            usuarioRequest.setPassword(usuario.getPassword());
            usuarioRequest.setTelefono(usuario.getTelefono());
            usuarioRequest.setIdRol(2L);
            System.out.println(usuarioRequest);

            try {
                webClientBuilder.build()
                        .put()
                        .uri("http://api-gateway/api/usuarios/" + existingUsuario.getIdUsuario())
                        .bodyValue(usuarioRequest)
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();

                redirectAttributes.addFlashAttribute("successMessage", "Datos actualizados correctamente.");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar los datos. Inténtalo de nuevo.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Usuario no encontrado.");
        }

        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        Usuario usuario = webClientBuilder.build() .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                .retrieve() .bodyToMono(Usuario.class)
                .block();
        model.addAttribute("usuario", usuario);
        return "profile";
    }

    @GetMapping("/historialCompras")
    public String historial(Model model, Principal principal) {
        Usuario usuario = webClientBuilder.build() .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                .retrieve() .bodyToMono(Usuario.class)
                .block();
        Long idUsuario = usuario.getIdUsuario();

        List<Venta> ventas = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/ventas/usuario/" + idUsuario)
                .retrieve() .bodyToFlux(Venta.class)
                .collectList() .block();

        model.addAttribute("compras", ventas);
        return "historialCompras";
    }

    @GetMapping("/success")
    public String succees(Principal principal,Model model) {
        Usuario usuario = webClientBuilder.build()
                .get()
                .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();

        model.addAttribute("usuario", usuario);
        return "success";
    }
}