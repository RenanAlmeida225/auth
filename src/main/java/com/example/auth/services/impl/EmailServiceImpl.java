package com.example.auth.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.auth.services.EmailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendMailMessage(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New User Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText("http://localhost:8080/api/auth/confirmation/" + token);
            this.emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("error on sending email");
        }
    }

}
