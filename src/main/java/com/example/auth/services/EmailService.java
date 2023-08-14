package com.example.auth.services;

public interface EmailService {
    void sendMailMessage(String name, String to, String token);
}