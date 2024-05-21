package ru.konstantinpetrov.mailresponse.backend.entity;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;

    // Getters and Setters
}