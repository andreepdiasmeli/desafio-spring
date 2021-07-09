package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.UpsertProductDTO;
import meli.bootcamp.desafio_spring.dtos.ProductDTO;
import meli.bootcamp.desafio_spring.entities.Category;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.ProductRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private static final Integer defaultPageSize = 5;

    
    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product getProductById(Long productId) throws ResourceNotFoundException {
        return productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product " + productId + " does not exist.")
        );
    }

    public Page<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber != null ? pageNumber : 0;
        pageSize = pageSize != null ? pageSize : defaultPageSize;

        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Product> paginatedProducts = this.productRepository.findAll(paging);
        
        return paginatedProducts.map(ProductDTO::toDTO);
    }

    public ProductDTO findProductById(Long productId) {
        Product product = getProductById(productId);
        return ProductDTO.toDTO(product);
    }

    public ProductDTO addProduct(UpsertProductDTO upsertProductDto) {
        Long categoryId = upsertProductDto.getCategoryId();
        Category category = this.categoryService.findById(categoryId);

        Product product = new Product(
            upsertProductDto.getProductName(),
            upsertProductDto.getType(),
            upsertProductDto.getBrand(),
            upsertProductDto.getColor(),
            upsertProductDto.getNotes(),
            category
        );

        Product savedProduct = this.productRepository.save(product);

        return ProductDTO.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long productId, UpsertProductDTO upsertProductDTO) {
        Product product = getProductById(productId);
        Category category = this.categoryService.findById(upsertProductDTO.getCategoryId());

        product.setName(upsertProductDTO.getProductName());
        product.setType(upsertProductDTO.getType());
        product.setBrand(upsertProductDTO.getBrand());
        product.setColor(upsertProductDTO.getColor());
        product.setNotes(upsertProductDTO.getNotes());
        product.setCategory(category);

        Product updatedProduct = this.productRepository.save(product);
        
        return ProductDTO.toDTO(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        
        this.productRepository.delete(product);
    }
}
