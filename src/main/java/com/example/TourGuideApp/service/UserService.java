package com.example.TourGuideApp.service;

import com.example.TourGuideApp.persistence.entity.RegistrationStatusEnum;
import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.RoleRepository;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailService emailService;

    public void register(UserEntity user) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationStatus(RegistrationStatusEnum.REGISTERED);

        // Generar un código de verificación
        String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);

        userRepository.save(user);

        // Enviar el correo de verificación
        emailService.sendEmail(user.getEmail(), "Verification Code", "Your verification code is: " + verificationCode, null);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(999999); // Código de 6 dígitos
        return String.format("%06d", code);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    public boolean authenticate(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public void verifyEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getRegistrationStatus() == RegistrationStatusEnum.REGISTERED) {
            user.setRegistrationStatus(RegistrationStatusEnum.EMAIL_VERIFIED);
            userRepository.save(user);
        }
    }

    public void completeProfile(String email, String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getRegistrationStatus() == RegistrationStatusEnum.EMAIL_VERIFIED) {
            user.setUsername(username);
            user.setRegistrationStatus(RegistrationStatusEnum.COMPLETED);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Email not verified or user not found");
        }
    }
}









