package com.alten.myshopbackend.controller;

import com.alten.myshopbackend.domain.CartItem;
import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.service.CartService;
import com.alten.myshopbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public List<CartItem> getCart(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        return cartService.getCart(user);
    }

    @PostMapping("/{productId}")
    public CartItem addToCart(@AuthenticationPrincipal UserDetails ud,
                              @PathVariable Long productId,
                              @RequestParam(defaultValue = "1") int qty) {
         User user = userService.findByEmail(ud.getUsername());
        return cartService.addToCart(user, productId, qty);
    }

    @PatchMapping("/{productId}")
    public CartItem updateQty(@AuthenticationPrincipal UserDetails ud,
                              @PathVariable Long productId,
                              @RequestParam int qty) {
         User user = userService.findByEmail(ud.getUsername());
        return cartService.updateQuantity(user, productId, qty);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal UserDetails ud,
                                           @PathVariable Long productId) {
        User user = userService.findByEmail(ud.getUsername());
        cartService.removeFromCart(user, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}