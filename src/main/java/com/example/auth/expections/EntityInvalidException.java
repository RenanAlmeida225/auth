package com.example.auth.expections;

public class EntityInvalidException extends RuntimeException {
    public EntityInvalidException(String message) {
        super(message);
    }
}
