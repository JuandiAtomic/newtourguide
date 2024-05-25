
package com.example.TourGuideApp.service;

import java.io.File;


public interface IEmailService {
    
    void sendEmail(String toUser, String subject, String message, File file);
    
    
    
}
