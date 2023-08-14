package com.example.auth.services;

public interface EmailService {
    void sendMailMessage(String to, String token);
}