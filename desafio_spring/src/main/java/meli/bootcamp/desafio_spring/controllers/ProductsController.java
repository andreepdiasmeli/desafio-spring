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
import org.springframework.http.HttpStatus;

import meli.bootcamp.desafio_spring.dtos.ProductDTO;
import meli.bootcamp.desafio_spring.dtos.UpsertProductDTO;
import meli.bootcamp.desafio_spring.entities.PaginationResult;
import meli.bootcamp.desafio_spring.services.ProductService;

@RestController
@RequestMapping("products")
public class ProductsController {
    
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PaginationResult<ProductDTO> getAllProducts(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        PaginationResult<ProductDTO> paginationResult = this.productService.getAllProducts(pageNumber, pageSize);

        return paginationResult;
    }
    
    @GetMapping("{productId}")
    public ProductDTO findProductById(@PathVariable Long productId) {
        return this.productService.findProductById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody @Valid UpsertProductDTO upsertProductDTO) {
        return this.productService.addProduct(upsertProductDTO);
    }

    @PutMapping("{productId}")
    public ProductDTO updateProduct(
            @PathVariable Long productId,
            @RequestBody @Valid UpsertProductDTO upsertProductDTO) {
        return this.productService.updateProduct(productId, upsertProductDTO);
    }

    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        this.productService.deleteProduct(productId);
    }
}
