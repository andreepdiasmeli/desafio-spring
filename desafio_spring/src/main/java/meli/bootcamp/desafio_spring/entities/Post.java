package meli.bootcamp.desafio_spring.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Promotion promotion;

    private LocalDateTime createdAt;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn
    private Seller seller;

    public Post() {
    }

    public Post(BigDecimal price, Seller seller) {
        this.price = price;
        this.seller = seller;
        this.createdAt = LocalDateTime.now();
    }

    public Post(BigDecimal price, Seller seller, Product product) {
        this.price = price;
        this.seller = seller;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

