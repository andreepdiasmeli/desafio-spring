package meli.bootcamp.desafio_spring.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePromotionDTO {
    private BigDecimal discount;
    private LocalDateTime expiresAt;

    public CreatePromotionDTO() {
    }

    public CreatePromotionDTO(BigDecimal discount, LocalDateTime expiresAt) {
        this.discount = discount;
        this.expiresAt = expiresAt;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
