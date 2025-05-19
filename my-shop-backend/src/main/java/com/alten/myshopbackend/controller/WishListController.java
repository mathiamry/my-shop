package com.alten.myshopbackend.controller;

import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.domain.WishListItem;
import com.alten.myshopbackend.service.UserService;
import com.alten.myshopbackend.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishService;
    private final UserService userService;
    public WishListController(WishListService wishService, UserService userService) {
        this.wishService = wishService;
        this.userService = userService;
    }

    @GetMapping
    public List<WishListItem> getWishList(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        return wishService.getWishList(user);
    }

    @PostMapping("/{productId}")
    public WishListItem add(@AuthenticationPrincipal UserDetails ud,
                            @PathVariable Long productId) {
        User user = userService.findByEmail(ud.getUsername());
        return wishService.addToWishList(user, productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> remove(@AuthenticationPrincipal UserDetails ud,
                                       @PathVariable Long productId) {
        User user = userService.findByEmail(ud.getUsername());
        wishService.removeFromWishList(user, productId);
        return ResponseEntity.noContent().build();
    }
}