package com.alten.myshopbackend.service.impl;

import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.repository.UserRepository;
import com.alten.myshopbackend.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + email));
    }
}