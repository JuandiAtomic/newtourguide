package com.example.TourGuideApp.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${email.sender}")
    private String emailTourGuide;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendEmail(String toUser, String subject, String message, File file) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom(emailTourGuide);
        mailMessage.setTo(toUser);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        
        mailSender.send(mailMessage);
    }
}

