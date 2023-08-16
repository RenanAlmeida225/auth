package com.example.auth.expections;

import java.time.Instant;


public record StandardException(Instant timestamp, int status, String error, String message) {

}
