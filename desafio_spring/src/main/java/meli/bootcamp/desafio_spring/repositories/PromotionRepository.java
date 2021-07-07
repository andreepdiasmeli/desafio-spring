package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
