package com.example.TourGuideApp.controllers;

import com.example.TourGuideApp.persistence.entity.UserEntity;
import com.example.TourGuideApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/complete-profile")
    public String showCompleteProfileForm(Model model) {
        model.addAttribute("profileForm", new CompleteProfileForm());
        return "completeProfile";
    }

    @PostMapping("/complete-profile")
    public String completeProfile(@ModelAttribute CompleteProfileForm form, @RequestParam String email) {
        userService.completeProfile(email, form.getUsername());
        return "redirect:/TourGuide/mi-cuenta";
    }

    public static class CompleteProfileForm {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

