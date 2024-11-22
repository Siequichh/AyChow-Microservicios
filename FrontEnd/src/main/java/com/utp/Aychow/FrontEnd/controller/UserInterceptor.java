package com.utp.Aychow.FrontEnd.controller;

import com.utp.Aychow.FrontEnd.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final WebClient.Builder webClientBuilder;

    public UserInterceptor(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (principal != null && modelAndView != null) {
            Usuario usuario = webClientBuilder.build()
                    .get()
                    .uri("http://api-gateway/api/usuarios/email?email=" + principal.getName())
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();
            modelAndView.addObject("userName", usuario.getNombre());
        }
    }
}
