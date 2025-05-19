package com.alten.myshopbackend.controller;

import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.repository.UserRepository;
import com.alten.myshopbackend.service.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class AccountController {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AccountController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/account")
    public ResponseEntity<User> register(@RequestBody RegisterDto dto) {
        if (userRepo.existsByEmail(dto.email)) {
            return ResponseEntity.badRequest().build();
        }
        User u = new User();
        u.setUsername(dto.username);
        u.setFirstname(dto.firstname);
        u.setEmail(dto.email);
        u.setPassword(encoder.encode(dto.password));
        u.setRoles(Set.of("ROLE_USER"));
        User saved = userRepo.save(u);
        return ResponseEntity.ok(saved);
    }
}
