package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SellerPostsDTO {
    private Long userId;
    private String userName;
    private List<PromotionalPostDTO> posts;

    public SellerPostsDTO() {
    }

    public SellerPostsDTO(Long userId, String userName, List<PromotionalPostDTO> posts) {
        this.userId = userId;
        this.userName = userName;
        this.posts = posts;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<PromotionalPostDTO> getPosts() {
        return posts;
    }

    public static SellerPostsDTO toDTO(Seller seller, List<Post> posts, Predicate<Post> predicate) {
        List<PromotionalPostDTO> promotionalPostList =  posts.stream()
                .filter(predicate)
                .map(PromotionalPostDTO::toDTO)
                .collect(Collectors.toList());

        return new SellerPostsDTO(seller.getId(), seller.getUsername(), promotionalPostList);
    }

}
