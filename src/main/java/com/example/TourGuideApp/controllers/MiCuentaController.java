
package com.example.TourGuideApp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/TourGuide")
public class MiCuentaController {

    @GetMapping("/mi-cuenta")
    public String showMiCuentaPage() {
        return "mi-cuenta"; // Retorna la vista de la página 'mi-cuenta'
    }
}

    


