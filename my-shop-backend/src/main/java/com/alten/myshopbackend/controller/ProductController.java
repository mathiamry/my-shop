package com.alten.myshopbackend.controller;

import com.alten.myshopbackend.domain.Product;
import com.alten.myshopbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId().toString())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")

    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ResponseEntity<Product> patchProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        Product updated = productService.patchProduct(id, updates);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name == 'admin@admin.com'")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
