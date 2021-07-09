package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotEmpty;

public class CreateUserDTO {

    @NotEmpty
    private String username;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
