package meli.bootcamp.desafio_spring.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserFollowingPostsDTO {
    
    private Long userId;
    private List<PostDTO> posts;
    

    public UserFollowingPostsDTO() {}

    public UserFollowingPostsDTO(Long userId, List<PostDTO> posts) {
        this.userId = userId;
        this.posts = posts;
    }

    public Long getUserId() {
        return this.userId;
    }

    public List<PostDTO> getPosts() {
        return this.posts;
    }
}
