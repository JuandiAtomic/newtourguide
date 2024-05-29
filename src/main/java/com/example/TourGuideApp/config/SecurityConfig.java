package com.example.TourGuideApp.config;

import com.example.TourGuideApp.security.CustomAuthenticationProvider;
import com.example.TourGuideApp.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        http
//            .csrf().disable()
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/TourGuide/login", "/TourGuide/register", "/TourGuide/verification", "/TourGuide/complete-info").permitAll()
                    .requestMatchers("/TourGuide/**").authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/TourGuide/login")
                    .defaultSuccessUrl("/TourGuide/mi-cuenta", true)
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .permitAll()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/TourGuide/login")
                    .maximumSessions(1)
                    .expiredUrl("/TourGuide/login")
            )
            .authenticationProvider(customAuthenticationProvider);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**", "/css/**", "/js/**", "/images/**");
    }
}







