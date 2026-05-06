package com.authapi.authapi;

import com.authapi.authapi.repository.UserRepository;
import com.authapi.authapi.service.CustomUserDetailsService;
import com.authapi.authapi.service.JwtService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, CustomUserDetailsService userDetailsService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void run(String... args) throws Exception{


//        if (!userRepository.existsByUsername("admin")) {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setEmail("admin@example.com");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setRoleEnums(Set.of(RoleEnum.ROLE_ADMIN));  // rol administrador
//            userRepository.save(admin);
//            System.out.println("Usuario admin creado");
//        }

//        if (!userRepository.existsByUsername("juan")){
//            User user = new User();
//            user.setUsername("juan");
//            user.setEmail("juan@example.com");
//            user.setPassword(passwordEncoder.encode("123456")); // ¡encriptada!
//            user.setRoleEnums(Set.of(RoleEnum.ROLE_USER));
//            userRepository.save(user);
//            System.out.println("Usuario juan guardado con contraseña encriptada");
//        }

        //Cargar el UserDetails desde la BD
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername("juan");
//
//        //Generar un token jwt para este usuario
//        String token = jwtService.generateToken(userDetails);
//        System.out.println("Token generado: "+token);
//
//        // Extraer y validar información del token
//        String username = jwtService.extractUsername(token);
//        boolean esValido = jwtService.validateToken(token, userDetails);
//        System.out.println("Username extraído: " + username);
//        System.out.println("¿Token válido?: " + esValido);
//
//
////        //crear un usuario de prueba
////        User user = new User();
////        user.setUsername("Johan");
////        user.setEmail("johan@gmail.com");
////        user.setPassword("1234");
////        user.setRoleEnums(Set.of(RoleEnum.ROLE_USER));
//
//        // Guardar en la base de datos
//
//        // Comprobar que se puede recuperar
//        userRepository.findByUsername("juan").ifPresent(u ->
//                System.out.println("Usuario recuperado: " + u.getUsername() + " - Roles: " + u.getRoleEnums())
//        );
    }
}
