package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Category;
import meli.bootcamp.desafio_spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
