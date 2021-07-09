package meli.bootcamp.desafio_spring.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import meli.bootcamp.desafio_spring.dtos.ProductDTO;
import meli.bootcamp.desafio_spring.dtos.UpsertProductDTO;
import meli.bootcamp.desafio_spring.services.ProductService;

@RestController
@RequestMapping("products")
public class ProductsController {
    
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public Page<ProductDTO> getAllProducts(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        return this.productService.getAllProducts(pageNumber, pageSize);
    }
    
    @Operation(summary = "Get a specific product")
    @GetMapping("{productId}")
    public ProductDTO findProductById(@PathVariable Long productId) {
        return this.productService.findProductById(productId);
    }

    @Operation(summary = "Create a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody @Valid UpsertProductDTO upsertProductDTO) {
        return this.productService.addProduct(upsertProductDTO);
    }

    @Operation(summary = "Update an existing product")
    @PutMapping("{productId}")
    public ProductDTO updateProduct(
            @PathVariable Long productId,
            @RequestBody @Valid UpsertProductDTO upsertProductDTO) {
        return this.productService.updateProduct(productId, upsertProductDTO);
    }

    @Operation(summary = "Delete an existing product")
    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        this.productService.deleteProduct(productId);
    }
}
