package com.example.TourGuideApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/TourGuide")
public class MiCuentaController {

    @GetMapping("/mi-cuenta")
    public String showMiCuentaPage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            // El usuario está autenticado
            // Puedes realizar operaciones adicionales si lo deseas
            return "mi-cuenta";
        } else {
            // El usuario no está autenticado, redirigir a la página de inicio de sesión
            return "redirect:/TourGuide/login";
        }
    }
}


    
    

