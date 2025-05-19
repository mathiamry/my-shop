package com.alten.myshopbackend.service;

import com.alten.myshopbackend.domain.CartItem;
import com.alten.myshopbackend.domain.User;

import java.util.List;

public interface CartService {
    List<CartItem> getCart(User user);
    CartItem addToCart(User user, Long productId, int qty);
    CartItem updateQuantity(User user, Long productId, int qty);
    void removeFromCart(User user, Long productId);
    void clearCart(User user);
}