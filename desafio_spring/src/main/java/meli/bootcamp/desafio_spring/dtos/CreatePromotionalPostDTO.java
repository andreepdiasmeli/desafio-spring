package meli.bootcamp.desafio_spring.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreatePromotionalPostDTO {

    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull @Positive
    private BigDecimal price;
    @NotNull @Valid
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
