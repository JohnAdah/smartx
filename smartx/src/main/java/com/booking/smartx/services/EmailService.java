package com.booking.smartx.services;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;


public interface EmailService {
    void sendEmail(String recipient, String body, String subject);
}
