package com.example.TourGuideApp.service;

import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IEmailService emailService;

    public void register(UserEntity user) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está en uso.");
        }

        // Generar código de verificación
        String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Enviar el correo de verificación
        String subject = "Código de verificación de TourGuide";
        String message = "¡Hola gran viajero! Gracias por unirte a TourGuide, tu código de verificación es: " + verificationCode;
        emailService.sendEmail(user.getEmail(), subject, message, null);
    }
    
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public UserEntity findByVerificationCode(String verificationCode) {
        return userRepository.findByVerificationCode(verificationCode);
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    public boolean authenticate(String email, String password) {
        // Implementación del método de autenticación
        UserEntity user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}



