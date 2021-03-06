package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
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
    private final SellerService sellerService;

    public PostService(
            PromotionService promotionService,
            PostRepository postRepository,
            ProductService productService, 
            UserService userService,
            SellerService sellerService) {
        this.promotionService = promotionService;
        this.postRepository = postRepository;
        this.userService = userService;
        this.productService = productService;
        this.sellerService = sellerService;
    }

    public UserFollowingPostsDTO getFollowerPosts(Long userId, String order) {
        User user = this.userService.findUserById(userId);

        List<Post> allSellerFollowedPosts = buildFollowingSellersPosts(user);

        Comparator<Post> dateComparator = getDateComparator(order);

        List<PostDTO> allSellerFollowedPostsDTO = allSellerFollowedPosts.stream()
            .sorted(dateComparator)
            .map(PostDTO::toDTO)
            .collect(Collectors.toList());

        return new UserFollowingPostsDTO(userId, allSellerFollowedPostsDTO);
    }

    private Comparator<Post> getDateComparator(String order) {
        if (order != null && !order.isEmpty() && order.equalsIgnoreCase("date_asc"))
            return (post1, post2) -> post1.getCreatedAt().compareTo(post2.getCreatedAt());

        return (post1, post2) -> post2.getCreatedAt().compareTo(post1.getCreatedAt());
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
                createPost.getPromotion()
        );
    }

    public PostDTO createPost(BigDecimal price, Long sellerId, Long productId, CreatePromotionDTO createPromotion) {
        Seller seller = this.sellerService.findSellerById(sellerId);
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
        Seller seller = sellerService.findSellerById(userId);
        Predicate<Post> predicate = getPromotionalPostFilter(isPromo);

        Sort sort = SortUtils.getPostSorterOf(order);

        List<Post> posts = postRepository.findAllBySeller_Id(userId, sort);

        SellerPostsDTO sellerPostsDTO = SellerPostsDTO.toDTO(seller, posts, predicate);
        return sellerPostsDTO;
    }

    public PostDTO getPostById(Long postId) {
        Post post = this.findPostById(postId);
        return PostDTO.toDTO(post);
    }

    public Post findPostById(Long postId) {
        return this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post with " + postId + " does not exist."));
    }

    public List<PostDTO> getAllPosts(String order) {
        Sort sorter = SortUtils.getPostSorterOf(order);
        List<Post> allPosts = Objects.isNull(sorter) ? this.postRepository.findAll() :
                                                       this.postRepository.findAll(sorter);
        return allPosts.stream().map(PostDTO::toDTO).collect(Collectors.toList());
    }

    public PostDTO updatePost(Long postId, CreatePostDTO updatePost) {
        Post post = this.findPostById(postId);
        Product product = this.productService.getProductById(updatePost.getProductId());
        post.setProduct(product);
        post.setPrice(updatePost.getPrice());
        this.handleUpdatePromotionPost(post, updatePost.getPromotion());
        post = this.postRepository.save(post);
        return PostDTO.toDTO(post);
    }

    public PostDTO updatePostPromotion(Long postId, CreatePromotionDTO updatePost) {
        Post post = this.findPostById(postId);
        if (Objects.isNull(post.getPromotion())) {
            throw new ResourceNotFoundException("Post with id " + postId + " is not a promotional post.");
        }
        this.promotionService.updatePromotion(post.getPromotion().getId(), updatePost);
        post = this.postRepository.save(post);
        return PostDTO.toDTO(post);
    }

    public void deletePost(Long postId) {
        Post post = this.findPostById(postId);
        this.userService.removePost(post);
        this.postRepository.deleteById(postId);
    }

    private void handleUpdatePromotionPost(Post post, CreatePromotionDTO promo) {
        if (Objects.isNull(post.getPromotion()) && !Objects.isNull(promo))
            this.promotionService.createPromotion(promo, post);
        else if(Objects.isNull(promo) && !Objects.isNull(post.getPromotion()))
            this.promotionService.deletePromotion(post.getPromotion(), post);
        else
            this.promotionService.updatePromotion(post.getPromotion().getId(), promo);
    }

    public Predicate<Post> getPromotionalPostFilter (boolean isPromo) {
        Predicate<Post> predicate = p -> true;
        if (isPromo) {
            predicate = p -> Objects.nonNull(p.getPromotion());
        }
        return predicate;

    }

}
