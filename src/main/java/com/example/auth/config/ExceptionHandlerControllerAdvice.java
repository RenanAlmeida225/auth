package com.example.auth.config;

import com.example.auth.expections.AuthenticationException;
import com.example.auth.expections.EntityInvalidExpection;
import com.example.auth.expections.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({EntityInvalidExpection.class})
    @ResponseBody
    public ResponseEntity<StandardException> entityInvalid(EntityInvalidExpection e) {
        StandardException re = new StandardException(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "entity invalid",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<StandardException> handleAuthenticationException(Exception ex) {
        StandardException re = new StandardException(Instant.now(), HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed at controller advice", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
