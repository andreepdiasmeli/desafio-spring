package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return this.postService.getFollowerPosts(userId, order);
    }
    
    @GetMapping("/{userId}/countPromo")
    public PromotionalCountDTO getPromotionalCountBySellerId(@PathVariable Long userId){
        return this.postService.getPromotionalCountBySellerId(userId);
    }

    @GetMapping("/{userId}/list")
    public SellerPostsDTO getPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) boolean onlyPromo){
        return this.postService.getPosts(userId, onlyPromo);
    }

    @PostMapping("newpost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody @Valid CreatePostDTO createPost) {
        return this.postService.createPost(createPost);
    }

    @PostMapping("newpromopost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPromotion(@RequestBody @Valid CreatePromotionalPostDTO createPromotionalPost) {
        return this.postService.createPost(createPromotionalPost);
    }
}
