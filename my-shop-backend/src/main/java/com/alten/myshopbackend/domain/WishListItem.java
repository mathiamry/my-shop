package com.alten.myshopbackend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wish_list_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","product_id"}))
public class WishListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "WishListItem{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}