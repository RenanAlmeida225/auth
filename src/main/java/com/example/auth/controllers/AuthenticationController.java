package com.example.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dtos.AuthenticationDto;
import com.example.auth.dtos.RegisterDto;
import com.example.auth.services.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto data) {
        this.service.register(data);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("confirmation/{token}")
    public ResponseEntity<Boolean> confirmation(@PathVariable String token) {
        boolean confirmEmail = this.service.confirmEmail(token);
        return ResponseEntity.ok(confirmEmail);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDto data) {
        String token = this.service.login(data);
        return ResponseEntity.ok(token);
    }

}
