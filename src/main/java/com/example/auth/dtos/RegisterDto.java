package com.example.auth.dtos;

import com.example.auth.entities.Role;

public record RegisterDto(String email, String password, Role role) {

}
