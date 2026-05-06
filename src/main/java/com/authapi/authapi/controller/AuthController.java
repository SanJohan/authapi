package com.authapi.authapi.controller;

import com.authapi.authapi.dto.*;
import com.authapi.authapi.model.RefreshToken;
import com.authapi.authapi.service.CustomUserDetailsService;
import com.authapi.authapi.service.JwtService;
import com.authapi.authapi.service.RefreshTokenService;
import com.authapi.authapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody RegisterRequestDTO requestDTO){
        return userService.registerUser(requestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String accessToken = jwtService.generateToken(userDetails);

         // Crear refresh token
         RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

         return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken.getToken(), "Bearer"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(refreshToken.getUser().getUsername());
        String newAccessToken = jwtService.generateToken(userDetails);

        // Rotación: eliminar el viejo y crear uno nuevo
        refreshTokenService.deleteByToken(requestRefreshToken);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new RefreshTokenResponseDTO(newAccessToken, newRefreshToken.getToken(), "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequestDTO request){
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.ok(Map.of("message","Logout exitoso"));
    }
}
