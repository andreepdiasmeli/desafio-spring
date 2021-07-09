package meli.bootcamp.desafio_spring.repositories;

import meli.bootcamp.desafio_spring.entities.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySeller_Id (Long sellerId, Sort sort);
}
