package com.alten.myshopbackend.service.dto;

public class TokenDto {
    public String token;
    public TokenDto(String token) { this.token = token; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

