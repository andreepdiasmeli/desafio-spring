package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotBlank;

public class CreateSellerDTO {

    @NotBlank
    private String username;

    public CreateSellerDTO() {
    }

    public CreateSellerDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
