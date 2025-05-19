package com.alten.myshopbackend.service.impl;

import com.alten.myshopbackend.domain.CartItem;
import com.alten.myshopbackend.domain.Product;
import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.repository.CartItemRepository;
import com.alten.myshopbackend.repository.ProductRepository;
import com.alten.myshopbackend.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public CartServiceImpl(CartItemRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<CartItem> getCart(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public CartItem addToCart(User user, Long productId, int qty) {
        Product p = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        CartItem item = cartRepo.findByUserAndProductId(user, productId)
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setUser(user);
                    ci.setProduct(p);
                    ci.setQuantity(0);
                    return ci;
                });
        item.setQuantity(item.getQuantity() + qty);
        return cartRepo.save(item);
    }

    @Override
    public CartItem updateQuantity(User user, Long productId, int qty) {
        CartItem item = cartRepo.findByUserAndProductId(user, productId)
                .orElseThrow(() -> new IllegalArgumentException("Item non présent"));
        item.setQuantity(qty);
        return cartRepo.save(item);
    }

    @Override
    public void removeFromCart(User user, Long productId) {
        cartRepo.findByUserAndProductId(user, productId)
                .ifPresent(cartRepo::delete);
    }

    @Override
    public void clearCart(User user) {
        cartRepo.deleteAll(cartRepo.findByUser(user));
    }
}
