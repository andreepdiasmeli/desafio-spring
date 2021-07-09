package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreatePostDTO {

    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull @Positive
    private BigDecimal price;
    private CreatePromotionDTO promotion;

    public CreatePostDTO() {
    }

    public CreatePostDTO(Long userId, Long productId, BigDecimal price) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
    }

    public CreatePostDTO(Long userId, Long productId, BigDecimal price, CreatePromotionDTO promotion) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.promotion = promotion;
    }

    public Long getUserId() {
        return userId;
    }

    public CreatePromotionDTO getPromotion() {
        return promotion;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
