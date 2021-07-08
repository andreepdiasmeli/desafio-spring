package meli.bootcamp.desafio_spring.dtos;

import java.math.BigDecimal;

public class CreatePromotionalPostDTO {
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private CreatePromotionDTO promotion;


    public CreatePromotionalPostDTO() {
    }

    public CreatePromotionalPostDTO(Long userId, Long productId, BigDecimal price, CreatePromotionDTO promotion) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.promotion = promotion;
    }

    public CreatePromotionDTO getPromotion() {
        return promotion;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
