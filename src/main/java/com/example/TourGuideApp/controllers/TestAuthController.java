package com.example.TourGuideApp.controllers;

import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import com.example.TourGuideApp.service.EmailAlreadyExistsException;
import com.example.TourGuideApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/TourGuide")
public class TestAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "crear_cuenta"; // Retorna la vista del formulario de registro
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity user, RedirectAttributes redirectAttributes) {
        try {
            userService.register(user); // Utiliza el método register del servicio para guardar los datos del usuario y enviar el correo de verificación
            redirectAttributes.addFlashAttribute("registerSuccess", "Registro exitoso. Revisa tu correo para el código de verificación.");
            return "redirect:/TourGuide/verification"; // Redirecciona a la página de verificación después del registro exitoso
        } catch (EmailAlreadyExistsException e) {
            // Manejar la excepción si es necesario
            redirectAttributes.addFlashAttribute("emailError", "El correo electrónico ya está en uso.");
            return "redirect:/TourGuide/register"; // Redirecciona de nuevo al formulario de registro con un mensaje de error
        }
    }

    @GetMapping("/verification")
    public String showVerificationPage() {
        return "verification-page"; // Retorna la vista de la página de verificación
    }

    @PostMapping("/verification")
    public String verifyCode(@RequestParam("verificationCode") String verificationCode, RedirectAttributes redirectAttributes) {
        UserEntity user = userService.findByVerificationCode(verificationCode);
        if (user != null && user.getVerificationCode().equals(verificationCode)) {
            user.setStatus(true); // Mark the user as verified
            userService.updateUser(user);
            return "redirect:/TourGuide/registro-exitoso"; // Redirecciona a la página de confirmación después de la verificación exitosa
        } else {
            redirectAttributes.addFlashAttribute("verificationError", "Código de verificación inválido. Inténtelo de nuevo.");
            return "redirect:/TourGuide/verification"; // Redirecciona de nuevo a la página de verificación con un mensaje de error
        }
    }

    @GetMapping("/registro-exitoso")
    public String showSuccessfulRegistrationPage() {
        return "registro-exitoso"; // Retorna la vista de la página de registro exitoso
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "iniciarsesion"; // Retorna la vista del formulario de inicio de sesión
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {
        boolean hasError = false;

        if (email.isEmpty()) {
            redirectAttributes.addFlashAttribute("emailError", "El campo de email no puede estar vacío.");
            hasError = true;
        }
        if (password.isEmpty()) {
            redirectAttributes.addFlashAttribute("passwordError", "El campo de contraseña no puede estar vacío.");
            hasError = true;
        }

        // Autenticar al usuario
        boolean authenticated = userService.authenticate(email, password);

        if (!hasError && !authenticated) {
            redirectAttributes.addFlashAttribute("emailError", "Upa... email o contraseña son incorrectas :/");
            redirectAttributes.addFlashAttribute("passwordError", "Upa... email o contraseña son incorrectas :/");
            hasError = true;
        }

        if (hasError) {
            return "redirect:/TourGuide/login";
        }

        // Obtener el usuario
        UserEntity user = userService.findByEmail(email);

        // Verificar el estado del usuario
        if (!user.isStatus()) {
            // El usuario no está verificado
            return "redirect:/TourGuide/verification";
        }

        return "redirect:/TourGuide/mi-cuenta"; // Redirecciona a la página de inicio después del inicio de sesión exitoso
    }
}
