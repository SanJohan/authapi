package com.authapi.authapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prueba")
public class PruebaController {

    @GetMapping
    public String prueba1(){
        return "Hola mundo";
    }

    @GetMapping("/dos")
    public String privado() {
        return "Acceso concedido a /prueba/dos con token válido";
    }

}
