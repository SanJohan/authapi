package com.authapi.authapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}
