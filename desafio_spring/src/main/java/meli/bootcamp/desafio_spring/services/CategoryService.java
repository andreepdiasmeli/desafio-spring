package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.Category;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.repositories.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryDTO> getAllCategories() {
        List<Category> category = this.categoryRepository.findAll();
        return category.stream().map(CategoryDTO::toDTO).collect(Collectors.toList());
    }

    public Category findCategoryById(Long idCategory) {
        Category category = this.categoryRepository.findById(idCategory).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + idCategory + " was not found."));
        return category;
    }

    public CategoryDTO getCategory(Long categoryId){
        Category category = findCategoryById(categoryId);
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

        Category category = findCategoryById(id);

        if (this.categoryRepository.existsByName(categoryDTO.getName())){
            throw new DuplicatedResourceException("Category with name " + categoryDTO.getName() + " already exists.");
        }

        category.setName(categoryDTO.getName());
        category = this.categoryRepository.save(category);
        return CategoryDTO.toDTO(category);
    }

    public void deleteCategory(Long idCategory) {
        findCategoryById(idCategory);
        try{
            this.categoryRepository.deleteById(idCategory);
        } catch (DataIntegrityViolationException e){
            throw new DuplicatedResourceException("you cannot delete a category id " + idCategory + " that has products.");
        }
    }
}
