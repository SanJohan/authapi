package com.authapi.authapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Deshabilitamos CSRF porque usaremos tokens JWT, no cookies
                .csrf(csrf -> csrf.disable())

                // No queremos sesiones de usuario en el servidor (STATELESS)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configuramos qué rutas necesitan autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/prueba", "/swagger-ui/**", "/v3/api-docs/**").permitAll() //publicas
                        .anyRequest().authenticated() //el resto requiere login
                );
//                // Permitimos que la consola H2 funcione en un iframe
//                .headers(headers -> headers
//                        .frameOptions(frame -> frame.sameOrigin())
//                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
