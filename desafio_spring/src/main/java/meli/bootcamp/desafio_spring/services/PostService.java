package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.PromotionalCountDTO;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    private UserService userService;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PromotionalCountDTO getPromotionalCountById(Long userId) {
        return userService.getPromoProductsCount(userId);
    }

}
