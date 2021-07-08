package meli.bootcamp.desafio_spring.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ManyToMany(mappedBy = "followers")
    private List<Seller> following = new ArrayList<>();

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Seller> getFollowing() {
        return following;
    }

    public void setFollowing(List<Seller> following) {
        this.following = following;
    }

    public void followSeller(Seller seller){
        following.add(seller);
    }

    public void unfollowSeller(Seller seller) {
        following.remove(seller);
        seller.getFollowers().remove(this);
    }

}
