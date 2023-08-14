package com.example.auth.services;

import com.example.auth.dtos.AuthenticationDto;
import com.example.auth.dtos.RegisterDto;

public interface AuthenticationService {
    void register(RegisterDto data);

    boolean confirmEmail(String token);

    String login(AuthenticationDto data);
}
