package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public SellerPromotionalPostsDTO getPromotionalPosts(@PathVariable Long userId){
        return this.postService.getPromotionalPosts(userId);
    }

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts(@RequestParam(required = false) String order) {
        return this.postService.getAllPosts(order);
    }

    @GetMapping("/posts/{postId}")
    public PostDTO getPostById(
            @PathVariable Long postId) {
        return this.postService.getPostById(postId);
    }

    @PostMapping("newpost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody @Valid CreatePostDTO createPost) {
        return this.postService.createPost(createPost);
    }

    @PostMapping("newpromopost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPromotionalPost(@RequestBody @Valid CreatePostDTO createPost) {
        return this.postService.createPost(createPost);
    }

    @PutMapping("posts/{postId}")
    public PostDTO updatePost(@PathVariable Long postId, @Valid @RequestBody CreatePostDTO updatePost) {
        return this.postService.updatePost(postId, updatePost);
    }

    @PutMapping("posts/promo/{postId}")
    public PostDTO updatePromotionPost(@PathVariable Long postId, @Valid @RequestBody CreatePromotionDTO updatePost) {
        return this.postService.updatePromotionPost(postId, updatePost);
    }

    @DeleteMapping("posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
    }
}
