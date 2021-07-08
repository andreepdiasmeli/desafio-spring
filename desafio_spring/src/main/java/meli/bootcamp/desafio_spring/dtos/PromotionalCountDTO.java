package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;

import java.util.Objects;

public class PromotionalCountDTO {
    private Long userId;
    private String userName;
    private Long promoProductsCount;

    public PromotionalCountDTO() {
    }

    public PromotionalCountDTO(Long userId, String userName, Long promoProductsCount) {
        this.userId = userId;
        this.userName = userName;
        this.promoProductsCount = promoProductsCount;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getPromoProductsCount() {
        return promoProductsCount;
    }

    public static PromotionalCountDTO toDTO(Seller seller) {
        Long promoProductsCount = seller.getPosts().stream().filter(x -> Objects.nonNull(x.getPromotion())).count();
        return new PromotionalCountDTO(seller.getId(), seller.getUsername(), promoProductsCount);
    }

}
