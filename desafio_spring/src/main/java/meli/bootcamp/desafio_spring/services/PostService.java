package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.repositories.PostRepository;
import meli.bootcamp.desafio_spring.util.SortUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
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

    public UserFollowingPostsDTO getFollowerPosts(Long userId, String order) {
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

    public PromotionalCountDTO getPromotionalCountBySellerId(Long userId) {
        return userService.getPromoProductsCount(userId);
    }

    public PostDTO createPost(CreatePostDTO createPost) {
        return this.createPost(
                createPost.getPrice(),
                createPost.getUserId(),
                createPost.getProductId(),
                null
        );
    }

    public PostDTO createPost(CreatePromotionalPostDTO createPromotionalPost) {
        return this.createPost(
                createPromotionalPost.getPrice(),
                createPromotionalPost.getUserId(),
                createPromotionalPost.getProductId(),
                createPromotionalPost.getPromotion()
        );
    }

    public PostDTO createPost(BigDecimal price, Long sellerId, Long productId, CreatePromotionDTO createPromotion) {
        Seller seller = this.userService.getSellerById(sellerId);
        Product product = this.productService.getProductById(productId);
        Post newPost = this.buildPost(price, seller, product, createPromotion);
        return PostDTO.toDTO(newPost);
    }

    private Post buildPost(BigDecimal price, Seller seller, Product product, CreatePromotionDTO createPromotion) {
        Post newPost = new Post(price, seller, product);
        if (!Objects.isNull(createPromotion))
            this.promotionService.createPromotion(createPromotion, newPost);
        return this.postRepository.save(newPost);
    }

    public SellerPostsDTO getPosts(Long userId, boolean isPromo, String order) {
        Seller seller = userService.getSellerById(userId);
        Predicate<Post> predicate = getPromotionalPostFilter(isPromo);

        Sort sort = SortUtils.getPostSorterOf(order);

        List<Post> posts = postRepository.findAllBySeller_Id(userId, sort);

        SellerPostsDTO sellerPostsDTO = SellerPostsDTO.toDTO(seller, posts, predicate);
        return sellerPostsDTO;
    }

    public Predicate<Post> getPromotionalPostFilter (boolean isPromo) {
        Predicate<Post> predicate = p -> true;
        if (isPromo) {
            predicate = p -> Objects.nonNull(p.getPromotion());
        }
        return predicate;

    }

}
