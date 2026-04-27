package com.authapi.authapi.security;

import com.authapi.authapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        System.out.println("Filtro JWT ejecutándose para URI: " + request.getRequestURI());
        System.out.println("Authorization header: " + authHeader);  // traza

        // Si no hay cabecera Authorization o no empieza con Bearer, seguimos sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        //username = jwtService.extractUsername(jwt); // Si el token es inválido, aquí lanzará excepción
        System.out.println("Token extraído: " + jwt);  // traza
        try {
            username = jwtService.extractUsername(jwt);
            System.out.println("Username extraído: " + username);  // traza
        } catch (Exception e) {
            System.out.println("Error al extraer username: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // Si tenemos username y no hay autenticación previa en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("UserDetails cargado: " + userDetails.getUsername());  // traza
            if (jwtService.validateToken(jwt, userDetails)) {
                System.out.println("Token válido!");  // traza
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token NO válido (validación fallida)");
            }
        } else {
            System.out.println("Username null o ya autenticado");
        }

        filterChain.doFilter(request, response);
    }
}