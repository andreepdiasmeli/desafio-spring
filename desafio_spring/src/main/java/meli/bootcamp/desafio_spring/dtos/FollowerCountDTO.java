package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;

public class FollowerCountDTO {

    private Long userId;
    private String userName;
    private Long followersCount;

    public FollowerCountDTO() {
    }

    public FollowerCountDTO(Long userId, String userName, Long followersCount) {
        this.userId = userId;
        this.userName = userName;
        this.followersCount = followersCount;
    }

    public static FollowerCountDTO toDTO(Seller seller){
        Long followersCount = seller.getFollowers().stream().count();
        return new FollowerCountDTO(seller.getId(), seller.getUsername(), followersCount);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getFollowersCount() {
        return followersCount;
    }
}
