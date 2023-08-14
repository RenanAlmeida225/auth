package com.example.auth.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.dtos.AuthenticationDto;
import com.example.auth.dtos.RegisterDto;
import com.example.auth.entities.Confirmation;
import com.example.auth.entities.User;
import com.example.auth.repositories.ConfirmationRepository;
import com.example.auth.repositories.UserRepository;
import com.example.auth.services.AuthenticationService;
import com.example.auth.services.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public void register(RegisterDto data) {
        if (this.userRepository.findByEmail(data.email()) != null)
            throw new RuntimeException("email not found");
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedPassword, data.role());
        Confirmation confirmation = new Confirmation(user);
        this.userRepository.save(user);
        this.confirmationRepository.save(confirmation);
        /* send email to confirm email */
        return;
    }

    @Override
    public boolean confirmEmail(String token) {
        Confirmation confirmation = this.confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("token invalid"));
        User user = confirmation.getUser();
        user.setEnabled(true);
        this.userRepository.save(user);
        this.confirmationRepository.delete(confirmation);
        return true;
    }

    @Override
    public String login(AuthenticationDto data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        return token;
    }

}
