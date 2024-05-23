//package com.example.TourGuideApp.service;
//
//import com.example.TourGuideApp.persistence.entity.UserEntity;
//import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public boolean existsByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
//
//    @Override
//    public UserEntity register(UserEntity user) {
//        // Implementa la lógica de validación y encriptación de la contraseña si es necesario
//        return userRepository.save(user);
//    }
//
//    @Override
//    public UserEntity findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public boolean authenticate(String email, String password) {
//        UserEntity user = findByEmail(email);
//        if (user != null) {
//            // Aquí deberías comparar la contraseña proporcionada con la almacenada (posiblemente encriptada)
//            return user.getPassword().equals(password);
//        }
//        return false;
//    }
//}


