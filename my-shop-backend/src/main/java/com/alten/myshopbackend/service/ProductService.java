package com.alten.myshopbackend.service;

import com.alten.myshopbackend.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product updateProduct(Long id, Product product);
    Product patchProduct(Long id, Map<String, Object> updates);
    void deleteProduct(Long id);
}
