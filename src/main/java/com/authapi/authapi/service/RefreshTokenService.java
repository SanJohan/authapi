package com.authapi.authapi.service;

import com.authapi.authapi.exception.RefreshTokenException;
import com.authapi.authapi.model.RefreshToken;
import com.authapi.authapi.model.User;
import com.authapi.authapi.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpirationMs;

    public RefreshToken createRefreshToken(String username){
        User user = userService.findByUsername(username);

        // Eliminar cualquier refresh token existente (así solo uno por usuario/dispositivo)
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("Refresh token expirado. Inicie sesión nuevamente.");
        }
        return token;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException("Refresh token no válido"));
    }

    @Transactional
    public void deleteByToken(String refreshToken) {
        RefreshToken token = findByToken(refreshToken);
        refreshTokenRepository.delete(token);
    }



}
