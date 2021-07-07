package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {}
