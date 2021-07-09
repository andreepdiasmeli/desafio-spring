package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
