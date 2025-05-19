package com.alten.myshopbackend.service;

import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.domain.WishListItem;

import java.util.List;

public interface WishListService {
    List<WishListItem> getWishList(User user);
    WishListItem addToWishList(User user, Long productId);
    void removeFromWishList(User user, Long productId);
}
