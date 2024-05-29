package com.example.TourGuideApp.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Implementación de authenticate() aquí...
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Implementación de supports() aquí...
        return false;
    }
}

