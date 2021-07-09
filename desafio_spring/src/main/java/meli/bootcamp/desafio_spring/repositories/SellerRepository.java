package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Seller;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findAllByFollowers_Id(Long userId, Sort sort);
    boolean existsByIdAndFollowers_Id(Long sellerId, Long userId);
}
