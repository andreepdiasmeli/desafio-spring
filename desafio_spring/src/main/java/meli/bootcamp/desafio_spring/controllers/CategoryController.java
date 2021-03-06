package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.CategoryDTO;
import meli.bootcamp.desafio_spring.dtos.CreateCategoryDTO;
import meli.bootcamp.desafio_spring.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @Operation(summary = "Get a specific category by ID")
    @Parameter(name = "idCategory", required = true, description = "The category ID", example = "1")
    @GetMapping("{idCategory}")
    public CategoryDTO getByIdCategory(@PathVariable Long idCategory) {
        return this.categoryService.getCategory(idCategory);
    }

    @Operation(summary = "Create a category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody @Valid CreateCategoryDTO createCategoryDTO){
        return this.categoryService.createCategory(createCategoryDTO);
    }

    @Operation(summary = "Update an existing category")
    @PutMapping("{idCategory}")
    public CategoryDTO updateCategory(@PathVariable Long idCategory, @RequestBody @Valid CreateCategoryDTO createCategoryDTO){
        return this.categoryService.updateCategory(idCategory, createCategoryDTO);
    }

    @Operation(summary = "Delete an existing category")
    @DeleteMapping("{idCategory}")
    public void deleteCategory(@PathVariable Long idCategory){
        this.categoryService.deleteCategory(idCategory);
    }
}
