package com.example.TourGuideApp;

import com.example.TourGuideApp.persistence.entity.RoleEntity;
import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.RoleRepository;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class TourGuideApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TourGuideApplication.class, args);
    }

    @Override
    public void run(String... args) {
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_ASSISTANT");

        // Crear usuarios con roles
        createUserWithRole("admin", "admin", "admin@example.com", "ROLE_USER");  // Cambia "ROLE_ADMIN" a "ROLE_USER" para evitar el uso del rol admin por ahora
    }

    @Transactional
    private void createRoleIfNotFound(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    @Transactional
    private void createUserWithRole(String username, String password, String email, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(password);  // Recuerda encriptar la contraseña antes de guardar
            user.setEmail(email);

            Optional<RoleEntity> role = roleRepository.findByName(roleName);
            if (role.isPresent()) {
                Set<RoleEntity> userRoles = new HashSet<>();
                userRoles.add(role.get());
                user.setRoles(userRoles);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Role not found: " + roleName);
            }
        }
    }
}
