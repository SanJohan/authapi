package com.authapi.authapi.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminDashboard(){
        return "Bienvenido al panel de administracion. Solo para administradores";
    }

    @GetMapping("/perfil-usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String perfilUsuario(){
        return "Perfil accesible para usuarios y administradores";
    }
}


