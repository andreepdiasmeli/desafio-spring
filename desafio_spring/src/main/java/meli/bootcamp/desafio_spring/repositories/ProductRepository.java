package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
