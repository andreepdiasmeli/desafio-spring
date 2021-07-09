package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Get all followed seller posts by User ID")
    @Parameter(
        name="order", 
        required = false, 
        description = "The order of the elements to be shown.")
    @GetMapping("/followed/{userId}/list")
    public UserFollowingPostsDTO getFollowerPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) String order) {
        return this.postService.getFollowerPosts(userId, order);
    }
    
    @Operation(summary = "Count number of promotional posts by Seller ID")
    @GetMapping("/{userId}/countPromo")
    public PromotionalCountDTO getPromotionalCountBySellerId(@PathVariable Long userId){
        return this.postService.getPromotionalCountBySellerId(userId);
    }

    @Operation(summary = "Get posts by Seller ID")
    @Parameter(
        name="order", 
        required = false, 
        description = "The order of the elements to be shown.")
    @GetMapping("/{userId}/list")
    public SellerPostsDTO getPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) boolean onlyPromo,
            @RequestParam(required = false) String order){
        return this.postService.getPosts(userId, onlyPromo, order);
    }

    @Operation(summary = "Get all posts")
    @Parameter(
        name="order", 
        required = false, 
        description = "The order of the elements to be shown.")
    @GetMapping("/posts")
    public List<PostDTO> getAllPosts(@RequestParam(required = false) String order) {
        return this.postService.getAllPosts(order);
    }

    @Operation(summary = "Get a specific post by ID")
    @GetMapping("/posts/{postId}")
    public PostDTO getPostById(
            @PathVariable Long postId) {
        return this.postService.getPostById(postId);
    }

    @Operation(summary = "Create a new post")
    @PostMapping("newpost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody @Valid CreatePostDTO createPost) {
        return this.postService.createPost(createPost);
    }

    @Operation(summary = "Create a new promotional post")
    @PostMapping("newpromopost")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPromotionalPost(@RequestBody @Valid CreatePostDTO createPost) {
        return this.postService.createPost(createPost);
    }

    @Operation(summary = "Update an existing post by ID")
    @PutMapping("posts/{postId}")
    public PostDTO updatePost(@PathVariable Long postId, @Valid @RequestBody CreatePostDTO updatePost) {
        return this.postService.updatePost(postId, updatePost);
    }

    @Operation(summary = "Update an existing promotional post by ID")
    @PutMapping("posts/promo/{postId}")
    public PostDTO updatePostPromotion(@PathVariable Long postId, @Valid @RequestBody CreatePromotionDTO updatePost) {
        return this.postService.updatePostPromotion(postId, updatePost);
    }

    @Operation(summary = "Delete a post by ID")
    @DeleteMapping("posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
    }
}
