package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.UserFollowingPostsDTO;
import meli.bootcamp.desafio_spring.services.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/followed/{userId}/list")
    public UserFollowingPostsDTO getFollowerPosts(@PathVariable Long userId) {
        UserFollowingPostsDTO sellerPostsResponseDTO = null;
        try {
            sellerPostsResponseDTO = this.postService.getFollowerPosts(userId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        return sellerPostsResponseDTO;
    }
    
}
