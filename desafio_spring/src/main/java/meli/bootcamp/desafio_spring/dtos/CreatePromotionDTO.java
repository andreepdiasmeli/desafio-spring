package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePromotionDTO {

    @NotNull
    @DecimalMax(value = "1.0")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal discount;

    @NotNull @Future
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
