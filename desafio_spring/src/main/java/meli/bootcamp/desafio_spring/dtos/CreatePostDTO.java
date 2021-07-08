package meli.bootcamp.desafio_spring.dtos;

import java.math.BigDecimal;

public class CreatePostDTO {
    private Long userId;
    private Long productId;
    private BigDecimal price;

    public CreatePostDTO() {
    }

    public CreatePostDTO(Long userId, Long productId, BigDecimal price) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
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
