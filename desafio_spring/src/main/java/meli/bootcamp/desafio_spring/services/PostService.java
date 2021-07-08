package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.*;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PromotionService promotionService;
    private final PostRepository postRepository;
    private final ProductService productService;
    private final UserService userService;

    public PostService(
            PromotionService promotionService,
            PostRepository postRepository, 
            ProductService productService, 
            UserService userService) {
        this.promotionService = promotionService;
        this.postRepository = postRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public UserFollowingPostsDTO getFollowerPosts(Long userId) throws ResourceNotFoundException {
        User user = this.userService.getUserById(userId);

        List<Post> allSellerFollowedPosts = buildFollowingSellersPosts(user);

        List<PostDTO> allSellerFollowedPostsDTO = allSellerFollowedPosts.stream()
            .map(PostDTO::toDTO)
            .sorted((post1, post2) -> post2.getDate().compareTo(post1.getDate()))
            .collect(Collectors.toList());

        return new UserFollowingPostsDTO(userId, allSellerFollowedPostsDTO);
    }

    private List<Post> buildFollowingSellersPosts(User user) {
        List<Post> allSellerFollowedPosts = new ArrayList<>();
        List<Seller> sellersFollowed = user.getFollowing();

        for (Seller seller : sellersFollowed) {
            List<Post> filteredPostsByDate = seller.getPosts().stream()
                .filter(post -> post.getCreatedAt().isAfter(LocalDateTime.now().minusWeeks(2)))
                .collect(Collectors.toList());

            allSellerFollowedPosts.addAll(filteredPostsByDate);
        }

        return allSellerFollowedPosts;
    }

    public PostDTO createPost(CreatePostDTO createPost) throws ResourceNotFoundException, InvalidParameterException {
        Seller seller = this.userService.getSellerById(createPost.getUserId());
        Product product = this.productService.getProductById(createPost.getProductId());
        return this.initPost(createPost.getPrice(), seller, product, null);
    }

    public PromotionalCountDTO getPromotionalCountBySellerId(Long userId) {
        return userService.getPromoProductsCount(userId);
    }

    public PostDTO createPost(CreatePromotionalPostDTO createPromotionalPost) throws ResourceNotFoundException,
            InvalidParameterException {
        Seller seller = this.userService.getSellerById(createPromotionalPost.getUserId());
        Product product = this.productService.getProductById(createPromotionalPost.getProductId());
        return this.initPost(createPromotionalPost.getPrice(), seller, product, createPromotionalPost.getPromotion());
    }

    private PostDTO initPost(BigDecimal price, Seller seller, Product product, CreatePromotionDTO createPromotion)
            throws InvalidParameterException {
        Post newPost = new Post(price, seller, product);
        if (!Objects.isNull(createPromotion))
            this.promotionService.createPromotion(createPromotion, newPost);
        this.postRepository.save(newPost);
        return PostDTO.toDTO(newPost);
    }
}
