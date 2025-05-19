package com.alten.myshopbackend.service;

import com.alten.myshopbackend.domain.User;

public interface UserService {
    User findByEmail(String email);
}
