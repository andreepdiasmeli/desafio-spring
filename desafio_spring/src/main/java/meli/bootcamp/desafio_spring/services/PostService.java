package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.PostDTO;
import meli.bootcamp.desafio_spring.dtos.SellerPostsResponseDTO;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.repositories.PostRepository;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public SellerPostsResponseDTO getFollowerPosts(Long userId) throws ResourceNotFoundException {
        User user = this.userService.getUserById(userId);

        List<Post> allSellerFollowedPosts = new ArrayList<>();
        List<Seller> sellersFollowed = user.getFollowing();

        for (Seller seller : sellersFollowed) {
            List<Post> filteredPostsByDate = seller.getPosts().stream()
                .filter(post -> post.getCreatedAt().isAfter(LocalDateTime.now().minusWeeks(2)))
                .collect(Collectors.toList());

            allSellerFollowedPosts.addAll(filteredPostsByDate);
        }

        List<PostDTO> allSellerFollowedPostsDTO = allSellerFollowedPosts.stream()
            .map(post -> PostDTO.toDTO(post))
            .collect(Collectors.toList());

        return new SellerPostsResponseDTO(userId, allSellerFollowedPostsDTO);
    }
}
