package com.authapi.authapi.service;

import com.authapi.authapi.dto.RegisterRequestDTO;
import com.authapi.authapi.dto.UserResponseDTO;
import com.authapi.authapi.exception.UserAlreadyExistsException;
import com.authapi.authapi.model.Role;
import com.authapi.authapi.model.RoleEnum;
import com.authapi.authapi.model.User;
import com.authapi.authapi.repository.RoleRepository;
import com.authapi.authapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("El username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol por defecto no econtrado"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(defaultRole));

        User saved = userRepository.save(user);

        Set<String> roleNames = saved.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return new UserResponseDTO(saved.getId(), saved.getUsername(), saved.getEmail(), roleNames);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe username"));
    }

    @Override
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("No existe username"));
    }
}
