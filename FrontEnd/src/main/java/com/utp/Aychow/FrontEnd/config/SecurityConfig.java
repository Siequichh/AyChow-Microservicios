package com.utp.Aychow.FrontEnd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/","/css/**", "/js/**", "/img/**","AyChow/**","/upload","/tienda","/favoritos","/conocenos","/login","/registro","/restaurarContraseña","/productos/**","/usuarios/**","/carrito/**","/historial").permitAll()
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers("/checkout")
                        .authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // Redirect to home or login page after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                ).sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) );

        return http.build();
    }
}
