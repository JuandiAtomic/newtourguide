package com.example.TourGuideApp.service;


import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity register(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está en uso.");
        }
        // Implementa la lógica de validación y encriptación de la contraseña si es necesario
        return userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String password) {
        UserEntity user = findByEmail(email);
        if (user != null) {
            // Aquí deberías comparar la contraseña proporcionada con la almacenada (posiblemente encriptada)
            return user.getPassword().equals(password);
        }
        return false;
    }
    
    
}






