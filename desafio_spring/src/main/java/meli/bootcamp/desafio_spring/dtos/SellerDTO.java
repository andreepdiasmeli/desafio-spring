package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;

public class SellerDTO {

    private Long userId;
    private String username;

    public SellerDTO() {
    }

    public SellerDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static SellerDTO toDTO(Seller seller) {
        return new SellerDTO(seller.getId(), seller.getUsername());
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

}
