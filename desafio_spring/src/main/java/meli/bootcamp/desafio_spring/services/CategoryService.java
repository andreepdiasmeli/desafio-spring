package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.Category;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long categoryId) {
        return this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category " + categoryId + " does not exist.")
        );
    }
}
