package com.alten.myshopbackend.service;

import com.alten.myshopbackend.domain.Product;
import com.alten.myshopbackend.domain.enumeration.InventoryStatus;
import com.alten.myshopbackend.repository.ProductRepository;
import com.alten.myshopbackend.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product existing;

    @BeforeEach
    void setUp() {
        existing = new Product();
        existing.setId(1L);
        existing.setCode("CODE1");
        existing.setName("Name1");
        existing.setDescription("Desc1");
        existing.setImage("img1.png");
        existing.setCategory("Cat1");
        existing.setPrice(new BigDecimal("10.00"));
        existing.setQuantity(5);
        existing.setInternalReference("REF1");
        existing.setShellId(100L);
        existing.setInventoryStatus(InventoryStatus.INSTOCK);
        existing.setRating(4);
    }

    @Test
    void createProduct_callsSaveAndReturns() {
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        Product created = productService.createProduct(existing);

        assertThat(created).isSameAs(existing);
        verify(productRepository).save(existing);
    }

    @Test
    void getAllProducts_returnsList() {
        List<Product> list = List.of(existing);
        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productService.getAllProducts();

        assertThat(result).containsExactly(existing);
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_existingId_returnsOptional() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));

        Optional<Product> opt = productService.getProductById(1L);

        assertThat(opt).isPresent()
                .contains(existing);
        verify(productRepository).findById(1L);
    }

    @Test
    void updateProduct_existing_updatesFields() {
        Product updated = getProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = productService.updateProduct(1L, updated);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCode()).isEqualTo(updated.getCode());
        assertThat(result.getPrice()).isEqualTo(updated.getPrice());
        assertThat(result.getQuantity()).isEqualTo(updated.getQuantity());
        assertThat(result.getInventoryStatus()).isEqualTo(updated.getInventoryStatus());
        verify(productRepository).save(existing);
    }

    private static Product getProduct() {
        Product updated = new Product();
        updated.setCode("CODE2");
        updated.setName("Name2");
        updated.setDescription("Desc2");
        updated.setImage("img2.png");
        updated.setCategory("Cat2");
        updated.setPrice(new BigDecimal("20.00"));
        updated.setQuantity(10);
        updated.setInternalReference("REF2");
        updated.setShellId(200L);
        updated.setInventoryStatus(InventoryStatus.LOWSTOCK);
        updated.setRating(3);
        return updated;
    }

    @Test
    void patchProduct_updatesSpecifiedFields() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "PatchedName");
        updates.put("price", "15.50");
        updates.put("inventoryStatus", "OUTOFSTOCK");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = productService.patchProduct(1L, updates);

        assertThat(result.getName()).isEqualTo("PatchedName");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("15.50"));
        assertThat(result.getInventoryStatus()).isEqualTo(InventoryStatus.OUTOFSTOCK);
        verify(productRepository).save(existing);
    }

    @Test
    void deleteProduct_callsDeleteById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void getProductById_nonExisting_returnsEmpty() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> opt = productService.getProductById(99L);

        assertThat(opt).isEmpty();
    }

    @Test
    void updateProduct_nonExisting_throwsException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(2L, existing))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Produit introuvable");
    }

    @Test
    void patchProduct_nonExisting_throwsException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Map<String, Object> updates = Map.of("name", "X");
        assertThatThrownBy(() -> productService.patchProduct(2L, updates))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Produit introuvable");
    }
}