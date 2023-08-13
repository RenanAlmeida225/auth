package com.example.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dtos.AuthenticationDto;
import com.example.auth.dtos.RegisterDto;
import com.example.auth.entities.Confirmation;
import com.example.auth.entities.User;
import com.example.auth.repositories.ConfirmationRepository;
import com.example.auth.repositories.UserRepository;
import com.example.auth.services.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final ConfirmationRepository confirmationRepository;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto data) {
        if (this.repository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedPassword, data.role());
        Confirmation confirmation = new Confirmation(user);
        this.repository.save(user);
        this.confirmationRepository.save(confirmation);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("confirmation/{token}")
    public ResponseEntity<Boolean> confirmation(@PathVariable String token) {
        Confirmation confirmation = this.confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("token invalid"));
        User user = confirmation.getUser();
        user.setEnabled(true);
        this.repository.save(user);
        this.confirmationRepository.delete(confirmation);
        return ResponseEntity.ok(true);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDto data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

}
