//package com.authapi.authapi;
//
//import com.authapi.authapi.model.Role;
//import com.authapi.authapi.model.User;
//import com.authapi.authapi.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//
//    public DataLoader(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception{
//        //crear un usuario de prueba
//        User user = new User();
//        user.setUsername("Johan");
//        user.setEmail("johan@gmail.com");
//        user.setPassword("1234");
//        user.setRoles(Set.of(Role.ROLE_USER));
//
//        // Guardar en la base de datos
//        userRepository.save(user);
//        System.out.println("Usuario guardado: " + user);
//
//        // Comprobar que se puede recuperar
//        userRepository.findByUsername("juan").ifPresent(u ->
//                System.out.println("Usuario recuperado: " + u.getUsername() + " - Roles: " + u.getRoles())
//        );
//    }
//}
