package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Category;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> category = this.categoryRepository.findAll();
        return category.stream().map(CategoryDTO::toDTO).collect(Collectors.toList());
    }

    public CategoryDTO getIdCategory(Long idCategory) {
        Category category = this.categoryRepository.findById(idCategory).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + idCategory + " was not found."));
        return CategoryDTO.toDTO(category);
    }

    public CategoryDTO createCategory(CreateCategoryDTO createCategory) {

        if (this.categoryRepository.existsByName(createCategory.getName())){
            throw new DuplicatedResourceException("Category with name " + createCategory.getName() + " already exists.");
        }
        Category category = new Category(createCategory.getName());

        category = this.categoryRepository.save(category);
        return CategoryDTO.toDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CreateCategoryDTO categoryDTO) {

        Category category = this.categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " does not exist."));

        if (this.categoryRepository.existsByName(categoryDTO.getName())){
            throw new DuplicatedResourceException("Category with name " + categoryDTO.getName() + " already exists.");
        }

        category.setName(categoryDTO.getName());
        category = this.categoryRepository.save(category);
        return CategoryDTO.toDTO(category);
    }

    public void deleteCategory(Long idCategory) {
        if (!this.categoryRepository.existsById(idCategory)) {
            throw new ResourceNotFoundException("Category with id " + idCategory + " does not exist.");
        }

        try{
            this.categoryRepository.deleteById(idCategory);
        } catch (DataIntegrityViolationException e){
            throw new DuplicatedResourceException("you cannot delete a category id " + idCategory + " that has products.");
        }

    }
}
