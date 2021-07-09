package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Post;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PostDTO {
    
    private Long sellerId;
    private Long postId;
    private LocalDateTime date;
    private ProductDTO detail;
    private BigDecimal price;
    private PromotionDTO promotion;


    public PostDTO() {}

    public PostDTO(
            Long sellerId,
            Long postId, 
            LocalDateTime date,
            ProductDTO detail, 
            BigDecimal price,
            PromotionDTO promotion) {
        this.sellerId = sellerId;
        this.postId = postId;
        this.date = date;
        this.detail = detail;
        this.price = price;
        this.promotion = promotion;
    }

    public Long getSellerId() {
        return this.sellerId;
    }

    public Long getPostId() {
        return this.postId;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public ProductDTO getDetail() {
        return this.detail;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public PromotionDTO getPromotion() {
        return this.promotion;
    }

    public static PostDTO toDTO(Post post) {
        ProductDTO postProductDTO = ProductDTO.toDTO(post.getProduct());
        PromotionDTO postPromotionDTO = PromotionDTO.toDTO(post.getPromotion());

        return new PostDTO(
            post.getSeller().getId(), 
            post.getId(),
            post.getCreatedAt(),
            postProductDTO,
            post.getPrice(),
            postPromotionDTO
        );
    }

}
