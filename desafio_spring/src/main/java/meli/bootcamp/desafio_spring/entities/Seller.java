package meli.bootcamp.desafio_spring.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller  extends User{

    /*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Category> categories = new ArrayList<>();
    */

    @ManyToMany
    @JoinTable(
            name = "follower_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private List<User> followers = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Seller() {
    }

    public Seller(String username) {
        super(username);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
