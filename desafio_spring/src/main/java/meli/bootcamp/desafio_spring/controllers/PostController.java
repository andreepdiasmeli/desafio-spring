package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.UserFollowingPostsDTO;
import meli.bootcamp.desafio_spring.services.PostService;

import org.springframework.http.HttpStatus;
import meli.bootcamp.desafio_spring.dtos.CreatePostDTO;
import meli.bootcamp.desafio_spring.dtos.PostDTO;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.dtos.PromotionalCountDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public UserFollowingPostsDTO getFollowerPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) String order) {
        UserFollowingPostsDTO sellerPostsResponseDTO = null;
        try {
            sellerPostsResponseDTO = this.postService.getFollowerPosts(userId, order);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        return sellerPostsResponseDTO;
    }
    
    @GetMapping("/{userId}/countPromo")
    public PromotionalCountDTO getPromotionalCountBySellerId(@PathVariable Long userId){
        try {
            return this.postService.getPromotionalCountBySellerId(userId);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PostMapping("newpost")
    public PostDTO createPost(@RequestBody CreatePostDTO createPost) {
        PostDTO result;
        try {
            result = this.postService.createPost(createPost);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return result;
    }
}
