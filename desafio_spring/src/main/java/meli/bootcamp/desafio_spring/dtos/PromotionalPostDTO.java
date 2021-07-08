package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Post;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PromotionalPostDTO {

    private Long postId;
    private LocalDate date;
    private ProductDTO detail;
    private BigDecimal price;
    private PromotionDTO promotion;


    public PromotionalPostDTO() {}

    public PromotionalPostDTO(
            Long postId,
            LocalDate date, 
            ProductDTO detail, 
            BigDecimal price,
            PromotionDTO promotion) {
        this.postId = postId;
        this.date = date;
        this.detail = detail;
        this.price = price;
        this.promotion = promotion;
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

    public static PromotionalPostDTO toDTO(Post post) {
        ProductDTO postProductDTO = ProductDTO.toDTO(post.getProduct());
        PromotionDTO postPromotionDTO = PromotionDTO.toDTO(post.getPromotion());

        return new PromotionalPostDTO(
            post.getId(),
            post.getCreatedAt().toLocalDate(),
            postProductDTO,
            post.getPrice(),
            postPromotionDTO
        );
    }

}
