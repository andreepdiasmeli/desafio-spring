package meli.bootcamp.desafio_spring.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import meli.bootcamp.desafio_spring.entities.Promotion;

public class PromotionDTO {
    
    private BigDecimal discount;
    private LocalDateTime expiresAt;


    public PromotionDTO() {}

    public PromotionDTO(BigDecimal discount, LocalDateTime expiresAt) {
        this.discount = discount;
        this.expiresAt = expiresAt;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public static PromotionDTO toDTO(Promotion promotion) {
        if (promotion == null) {
            return null;
        }
        
        return new PromotionDTO(promotion.getDiscount(), promotion.getExpiresAt());
    }

}
