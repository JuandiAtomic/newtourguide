package com.example.TourGuideApp.controllers;

import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
import com.example.TourGuideApp.service.EmailAlreadyExistsException;
import com.example.TourGuideApp.service.UserService; // Agregar importación del servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/TourGuide")
public class TestAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Inyectar el servicio UserService

    @GetMapping("/register")
    public String showRegisterForm() {
        return "crear_cuenta"; // Retorna la vista del formulario de registro
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity user, RedirectAttributes redirectAttributes) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                redirectAttributes.addFlashAttribute("emailError", "El correo electrónico ya está en uso.");
                return "redirect:/TourGuide/register"; // Redirecciona de nuevo al formulario de registro con un mensaje de error
            } else {
                userService.register(user); // Utilizar el método register del servicio
                return "redirect:/TourGuide/login"; // Redirecciona al formulario de inicio de sesión después del registro exitoso
            }
        } catch (EmailAlreadyExistsException e) {
            // Manejar la excepción si es necesario
            redirectAttributes.addFlashAttribute("emailError", "El correo electrónico ya está en uso.");
            return "redirect:/TourGuide/register"; // Redirecciona de nuevo al formulario de registro con un mensaje de error
        }
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
        if (!hasError && !userService.authenticate(email, password)) { // Utilizar el método authenticate del servicio
            redirectAttributes.addFlashAttribute("emailError", "Upa... email o contraseña son incorrectas :/");
            redirectAttributes.addFlashAttribute("passwordError", "Upa... email o contraseña son incorrectas :/");
            hasError = true;
        }
        if (hasError) {
            return "redirect:/TourGuide/login";
        }
        return "redirect:/TourGuide/mi-cuenta"; // Redirecciona a la página de inicio después del inicio de sesión exitoso
    }

}
