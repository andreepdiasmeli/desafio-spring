package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.CategoryDTO;
import meli.bootcamp.desafio_spring.dtos.CreateCategoryDTO;
import meli.bootcamp.desafio_spring.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAllCategorys() {
        return this.categoryService.getAllCategorys();
    }

    @GetMapping("{idCategory}")
    public CategoryDTO getByIdCategory(@PathVariable Long idCategory) {
        return this.categoryService.getIdCategory(idCategory);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        return this.categoryService.createCategory(createCategoryDTO);
    }

    @PutMapping("{idCategory}")
    public CategoryDTO updateCategory(@PathVariable Long idCategory, @RequestBody CreateCategoryDTO createCategoryDTO){
        return this.categoryService.updateCategory(idCategory, createCategoryDTO);
    }

    @DeleteMapping("{idCategory}")
    public void deleteCategory(@PathVariable Long idCategory){
        this.categoryService.deleteCategory(idCategory);
    }
}
