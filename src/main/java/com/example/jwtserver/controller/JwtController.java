package com.example.jwtserver.controller;

import com.example.jwtserver.exception.InvalidJWTTokenException;
import com.example.jwtserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    JwtService jwtService;

    @RequestMapping(value = "api/v1/token")
    public String getToken() {
        return jwtService.getToken();
    }

    @RequestMapping(value = "api/v1/resource")
    public String getProtectedResource(@RequestHeader("Token") String token) {
        return jwtService.getProtectResource(token);
    }

    @ExceptionHandler(InvalidJWTTokenException.class)
    final ResponseEntity<String> handleInvalidJWTTokenException(InvalidJWTTokenException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
