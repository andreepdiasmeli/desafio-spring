package meli.bootcamp.desafio_spring.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import meli.bootcamp.desafio_spring.entities.Post;

public class PostDTO {
    
    private Long sellerId;
    private Long postId;
    private LocalDate date;
    private ProductDTO detail;
    private BigDecimal price;
    private PromotionDTO promotion;


    public PostDTO() {}

    public PostDTO(
            Long sellerId,
            Long postId, 
            LocalDate date, 
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

    public LocalDate getDate() {
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
            post.getCreatedAt().toLocalDate(),
            postProductDTO,
            post.getPrice(),
            postPromotionDTO
        );
    }

}
