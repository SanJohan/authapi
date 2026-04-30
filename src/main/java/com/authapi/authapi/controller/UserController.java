package com.authapi.authapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/datos")
    @PreAuthorize("hasRole('USER')")
    public String datosUsuario(){
        return "Estos son tus datos personales. Solo para usuarios con rol USER";
    }
}
