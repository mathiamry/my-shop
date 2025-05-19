package com.alten.myshopbackend.service.impl;

import com.alten.myshopbackend.domain.enumeration.InventoryStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alten.myshopbackend.domain.Product;
import com.alten.myshopbackend.repository.ProductRepository;
import com.alten.myshopbackend.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setCode(product.getCode());
                    existing.setName(product.getName());
                    existing.setDescription(product.getDescription());
                    existing.setImage(product.getImage());
                    existing.setCategory(product.getCategory());
                    existing.setPrice(product.getPrice());
                    existing.setQuantity(product.getQuantity());
                    existing.setInternalReference(product.getInternalReference());
                    existing.setShellId(product.getShellId());
                    existing.setInventoryStatus(product.getInventoryStatus());
                    existing.setRating(product.getRating());
                    existing.setUpdatedAt(product.getUpdatedAt());
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    @Override
    public Product patchProduct(Long id, Map<String, Object> updates) {
        return productRepository.findById(id)
                .map(existing -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "name": existing.setName((String) value); break;
                            case "price": existing.setPrice(new java.math.BigDecimal(value.toString())); break;
                            case "quantity": existing.setQuantity((Integer) value); break;
                            case "inventoryStatus": existing.setInventoryStatus(
                                    InventoryStatus.valueOf(((String) value))
                            ); break;
                            // handle other fields as needed
                            default: break;
                        }
                    });
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
