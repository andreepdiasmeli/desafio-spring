package meli.bootcamp.desafio_spring.dtos;

import javax.validation.constraints.NotNull;

public class CreateCategoryDTO {

    @NotNull
    private String name;

    public CreateCategoryDTO(){}

    public CreateCategoryDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
