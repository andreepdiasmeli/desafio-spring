package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SellerPromotionalPostsDTO {
    private Long userId;
    private String userName;
    private List<PromotionalPostDTO> posts;

    public SellerPromotionalPostsDTO() {
    }

    public SellerPromotionalPostsDTO(Long userId, String userName, List<PromotionalPostDTO> posts) {
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

    public static SellerPromotionalPostsDTO toDTO(Seller seller) {
        List<PromotionalPostDTO> promotionalPostList =  seller.getPosts()
                .stream()
                .filter(x -> Objects.nonNull(x.getPromotion()))
                .map(PromotionalPostDTO::toDTO)
                .collect(Collectors.toList());

        return new SellerPromotionalPostsDTO(seller.getId(), seller.getUsername(), promotionalPostList);
    }
}
