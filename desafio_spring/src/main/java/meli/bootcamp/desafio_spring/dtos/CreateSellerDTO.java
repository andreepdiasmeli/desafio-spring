package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotEmpty;

public class CreateSellerDTO {

    @NotEmpty
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
