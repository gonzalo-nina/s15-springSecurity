package com.example.utp1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Definir un usuario con rol USER
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("usuario")
                .password("password")
                .roles("USER")
                .build();

        // Definir un administrador con rol ADMIN
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configurar CORS y deshabilitar CSRF para simplificar
                .cors(Customizer.withDefaults())
                .csrf().disable()
                // Definir autorizaciones de URL
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // Requiere autenticación para /api/**
                        .anyRequest().permitAll() // Permite todas las demás solicitudes
                )
                // Configurar el método de autenticación
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
