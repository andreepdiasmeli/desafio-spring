package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByFollowing_Id(Long sellerId, Sort sort);
}
