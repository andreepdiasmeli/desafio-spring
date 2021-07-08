package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class FollowingDTO {

    private Long userId;
    private String userName;
    private List<SellerDTO> followed;

    public FollowingDTO() {
    }

    public FollowingDTO(Long userId, String userName, List<SellerDTO> followed) {
        this.userId = userId;
        this.userName = userName;
        this.followed = followed;
    }

    public static FollowingDTO toDTO(User user) {
        List<SellerDTO> followed = user.getFollowing().stream().map(SellerDTO::toDTO).collect(Collectors.toList());
        return new FollowingDTO(user.getId(), user.getUsername(), followed);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<SellerDTO> getFollowed() {
        return followed;
    }
}
