package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.PostService;

import org.springframework.http.HttpStatus;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }

        return sellerPostsResponseDTO;
    }
    
    @GetMapping("/{userId}/countPromo")
    public PromotionalCountDTO getPromotionalCountBySellerId(@PathVariable Long userId){
        try {
            return this.postService.getPromotionalCountBySellerId(userId);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }

    }

    @GetMapping("/{userId}/list")
    public SellerPromotionalPostsDTO getPromotionalPosts(@PathVariable Long userId){
        try {
            return this.postService.getPromotionalPosts(userId);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }

    }

    @PostMapping("newpost")
    public PostDTO createPost(@RequestBody CreatePostDTO createPost) {
        PostDTO result;
        try {
            result = this.postService.createPost(createPost);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
        return result;
    }
}
