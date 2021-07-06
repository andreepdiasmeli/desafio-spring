package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
