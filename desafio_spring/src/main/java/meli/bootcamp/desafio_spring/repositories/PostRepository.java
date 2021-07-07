package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
