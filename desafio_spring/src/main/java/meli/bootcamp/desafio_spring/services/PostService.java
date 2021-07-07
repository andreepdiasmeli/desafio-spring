package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.CreatePostDTO;
import meli.bootcamp.desafio_spring.dtos.PostDTO;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Product;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ProductService productService;
    private final UserService userService;

    public PostService(PostRepository postRepository, ProductService productService, UserService userService) {
        this.postRepository = postRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public PostDTO createPost(CreatePostDTO createPost) throws ResourceNotFoundException {
        Seller seller = this.userService.getSeller(createPost.getUserId());
        Post newPost = new Post(createPost.getPrice(), seller);
        Product product = this.productService.getProduct(createPost.getProductId());
        newPost.setProduct(product);
        this.postRepository.save(newPost);
        return PostDTO.toDTO(newPost);
    }
}
