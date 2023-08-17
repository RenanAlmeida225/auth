package com.example.auth.config;

import com.example.auth.expections.AuthenticationException;
import com.example.auth.expections.EntityInvalidException;
import com.example.auth.expections.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({EntityInvalidException.class})
    @ResponseBody
    public ResponseEntity<StandardException> handleEntityInvalid(EntityInvalidException e) {
        StandardException re = new StandardException(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "entity invalid",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<StandardException> handleAuthenticationException(Exception ex) {
        StandardException re = new StandardException(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed at controller advice",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<StandardException> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Object message = Arrays.stream(Objects.requireNonNull(ex.getDetailMessageArguments())).toArray()[1];
        StandardException re = new StandardException(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "method argument not valid",
                message.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }
}
