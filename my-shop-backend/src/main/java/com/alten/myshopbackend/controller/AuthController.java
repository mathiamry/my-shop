package com.alten.myshopbackend.controller;

import com.alten.myshopbackend.security.JwtTokenProvider;
import com.alten.myshopbackend.service.dto.LoginDto;
import com.alten.myshopbackend.service.dto.TokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;

    public AuthController(AuthenticationManager authManager, JwtTokenProvider jwtProvider) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto login) {
        var auth = new UsernamePasswordAuthenticationToken(login.email, login.password);
        var result = authManager.authenticate(auth);
        String token = jwtProvider.generateToken(result);
        return ResponseEntity.ok(new TokenDto(token));
    }
}