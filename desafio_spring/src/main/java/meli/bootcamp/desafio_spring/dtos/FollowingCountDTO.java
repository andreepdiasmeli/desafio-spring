package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.User;

public class FollowingCountDTO {

    private Long userId;
    private String userName;
    private Long followingCount;

    public FollowingCountDTO() {
    }

    public FollowingCountDTO(Long userId, String userName, Long followingCount) {
        this.userId = userId;
        this.userName = userName;
        this.followingCount = followingCount;
    }

    public static FollowingCountDTO toDTO(User user){
        Long followingCount = user.getFollowing().stream().count();
        return new FollowingCountDTO(user.getId(), user.getUsername(), followingCount);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getFollowingCount() {
        return followingCount;
    }
}
