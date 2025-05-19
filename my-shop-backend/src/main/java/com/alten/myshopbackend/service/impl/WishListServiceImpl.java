package com.alten.myshopbackend.service.impl;

import com.alten.myshopbackend.domain.Product;
import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.domain.WishListItem;
import com.alten.myshopbackend.repository.ProductRepository;
import com.alten.myshopbackend.repository.WishListItemRepository;
import com.alten.myshopbackend.service.WishListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishListServiceImpl implements WishListService {

    private final WishListItemRepository wishRepo;
    private final ProductRepository productRepo;

    public WishListServiceImpl(WishListItemRepository wishRepo, ProductRepository productRepo) {
        this.wishRepo = wishRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<WishListItem> getWishList(User user) {
        return wishRepo.findByUser(user);
    }

    @Override
    public WishListItem addToWishList(User user, Long productId) {
        if (wishRepo.findByUserAndProductId(user, productId).isPresent()) {
            throw new IllegalArgumentException("Déjà dans la liste d'envie");
        }
        Product p = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        WishListItem w = new WishListItem();
        w.setUser(user);
        w.setProduct(p);
        return wishRepo.save(w);
    }

    @Override
    public void removeFromWishList(User user, Long productId) {
        wishRepo.findByUserAndProductId(user, productId)
                .ifPresent(wishRepo::delete);
    }
}
