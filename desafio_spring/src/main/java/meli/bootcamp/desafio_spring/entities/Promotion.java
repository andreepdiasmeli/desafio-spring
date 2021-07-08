package meli.bootcamp.desafio_spring.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal discount;
    private LocalDateTime expiresAt;

    @OneToOne(mappedBy = "promotion")
    private Post post;

    public Promotion() {
    }

    public Promotion(BigDecimal discount, LocalDateTime expiresAt, Post post) {
        this.discount = discount;
        this.expiresAt = expiresAt;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Post getPost() {
        return post;
    }
}