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

    @ManyToMany
    @JoinTable(
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "seller_id", "user_id" })},
            name = "follower_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private List<Seller> following = new ArrayList<>();

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public void followSeller(Seller seller){
        following.add(seller);
    }

    public void unfollowSeller(Seller seller) {
        following.remove(seller);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void removeFollowing(Seller seller){
        this.getFollowing().remove(seller);
    }
}
