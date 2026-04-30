package com.authapi.authapi.service;

import com.authapi.authapi.dto.RegisterRequestDTO;
import com.authapi.authapi.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(RegisterRequestDTO request);
}
