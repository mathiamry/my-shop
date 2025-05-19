package com.alten.myshopbackend.repository;

import com.alten.myshopbackend.domain.User;
import com.alten.myshopbackend.domain.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
    List<WishListItem> findByUser(User user);
    Optional<WishListItem> findByUserAndProductId(User user, Long productId);
}