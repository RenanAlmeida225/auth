package com.example.auth.services;

import com.example.auth.entities.User;

public interface TokenService {
    String generateToken(User user);

    String validateToken(String token);
}
