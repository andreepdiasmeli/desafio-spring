package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.entities.Seller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PostDTO {
    private Long postId;
    private ProductDTO product;
    private LocalDateTime createdAt;
    private Long sellerId;
    private BigDecimal price;
    public PostDTO() {
    }

    public PostDTO(Long id, Product product, LocalDateTime createdAt, Long sellerId, BigDecimal price) {
        this.postId = id;
        this.product = ProductDTO.toDTO(product);
        this.createdAt = createdAt;
        this.sellerId = sellerId;
        this.price = price;
    }

    public static PostDTO toDTO(Post post) {
        Seller seller = post.getSeller();
        PostDTO nPostDTO = new PostDTO(
            post.getId(),
            post.getProduct(),
            post.getCreatedAt(),
            seller.getId(),
            post.getPrice()
        );
        return nPostDTO;
    }

    public Long getSellerPostId() {
        return sellerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return this.postId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getSeller() {
        return sellerId;
    }
}
