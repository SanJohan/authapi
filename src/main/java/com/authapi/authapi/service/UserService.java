package com.authapi.authapi.service;

import com.authapi.authapi.dto.RegisterRequestDTO;
import com.authapi.authapi.dto.UserResponseDTO;
import com.authapi.authapi.model.User;

public interface UserService {
    UserResponseDTO registerUser(RegisterRequestDTO request);
    User findByUsername(String username);
    User findById(Long userId);
}
