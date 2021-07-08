package meli.bootcamp.desafio_spring.dtos;

import meli.bootcamp.desafio_spring.entities.User;

public class UserDTO {

    private Long userId;
    private String userName;

    public UserDTO() {
    }

    public UserDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}