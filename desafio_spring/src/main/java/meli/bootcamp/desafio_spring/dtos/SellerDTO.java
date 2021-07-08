package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;

public class SellerDTO {

    private Long userId;
    private String userName;

    public SellerDTO() {
    }

    public SellerDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static SellerDTO toDTO(Seller seller) {
        return new SellerDTO(seller.getId(), seller.getUsername());
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

}
