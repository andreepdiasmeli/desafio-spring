package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class FollowersDTO {

    private Long userId;
    private String userName;
    private List<UserDTO> followers;

    public FollowersDTO() {
    }

    public FollowersDTO(Long userId, String userName, List<UserDTO> followers) {
        this.userId = userId;
        this.userName = userName;
        this.followers = followers;
    }


    public static FollowersDTO  toDTO(Seller seller, List<User> followers){
        List<UserDTO> followersDTO = followers.stream().map(UserDTO::toDTO).collect(Collectors.toList());
        return new FollowersDTO(seller.getId(), seller.getUsername(), followersDTO);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }
}
