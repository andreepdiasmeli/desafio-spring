package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return this.productRepository.getById(productId);
    }
}
