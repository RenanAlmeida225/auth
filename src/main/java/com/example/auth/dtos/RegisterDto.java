package com.example.auth.dtos;

import com.example.auth.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@Email String email, @NotBlank String password, Role role) {

}
