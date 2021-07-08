package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.PostDTO;
import meli.bootcamp.desafio_spring.dtos.UserFollowingPostsDTO;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.dtos.PromotionalCountDTO;
import meli.bootcamp.desafio_spring.dtos.CreatePostDTO;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.repositories.PostRepository;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ProductService productService;
    private final UserService userService;

    public PostService(
            PostRepository postRepository, 
            ProductService productService, 
            UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public UserFollowingPostsDTO getFollowerPosts(Long userId, String order) throws ResourceNotFoundException {
        User user = this.userService.getUserById(userId);

        List<Post> allSellerFollowedPosts = buildFollowingSellersPosts(user);

        Comparator<PostDTO> dateComparator = getDateComparator(order);

        List<PostDTO> allSellerFollowedPostsDTO = allSellerFollowedPosts.stream()
            .map(PostDTO::toDTO)
            .sorted(dateComparator)
            .collect(Collectors.toList());

        return new UserFollowingPostsDTO(userId, allSellerFollowedPostsDTO);
    }

    private Comparator<PostDTO> getDateComparator(String order) {
        if (order != null && !order.isEmpty() && order.equalsIgnoreCase("date_asc"))
            return (post1, post2) -> post1.getDate().compareTo(post2.getDate());

        return (post1, post2) -> post2.getDate().compareTo(post1.getDate());
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

    public PostDTO createPost(CreatePostDTO createPost) throws ResourceNotFoundException {
        Seller seller = this.userService.getSellerById(createPost.getUserId());
        Product product = this.productService.getProductById(createPost.getProductId());
        Post newPost = new Post(createPost.getPrice(), seller, product);

        this.postRepository.save(newPost);
        
        return PostDTO.toDTO(newPost);
    }

    public PromotionalCountDTO getPromotionalCountBySellerId(Long userId) {
        return userService.getPromoProductsCount(userId);
    }

}
