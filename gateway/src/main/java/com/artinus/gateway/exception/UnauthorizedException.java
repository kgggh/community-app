package com.artinus.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
