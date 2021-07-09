package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Category;

public class CategoryDTO {
    
    private Long id;
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
            category.getId(),
            category.getName()
        );
    }
    
}
