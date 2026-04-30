package com.authapi.authapi.controller;

import com.authapi.authapi.dto.AuthResponseDTO;
import com.authapi.authapi.dto.LoginRequestDTO;
import com.authapi.authapi.dto.RegisterRequestDTO;
import com.authapi.authapi.dto.UserResponseDTO;
import com.authapi.authapi.service.JwtService;
import com.authapi.authapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody RegisterRequestDTO requestDTO){
        return userService.registerUser(requestDTO);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String token = jwtService.generateToken(userDetails);
         return new AuthResponseDTO(token, "Bearer");
    }
}
