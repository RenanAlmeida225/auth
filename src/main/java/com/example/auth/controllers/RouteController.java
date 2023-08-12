package com.example.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RouteController {

    @GetMapping("admin")
    public ResponseEntity<String> routeAdmin() {
        return ResponseEntity.ok().body("Route Admin");
    }

    @GetMapping("user")
    public ResponseEntity<String> routeUser() {
        return ResponseEntity.ok().body("Route User");
    
    }
}
