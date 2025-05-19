package com.alten.myshopbackend.repository;

import com.alten.myshopbackend.domain.CartItem;
import com.alten.myshopbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<   CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProductId(User user, Long productId);
}
