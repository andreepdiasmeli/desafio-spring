package meli.bootcamp.desafio_spring.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller  extends User{

    @ManyToMany
    @JoinTable(
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "seller_id", "user_id" })},
            name = "follower_following",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;

    public Seller() {}

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

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addFollower(User user){ this.followers.add(user); }

    public void removeFollower(User user){
        this.followers.remove(user);
    }

}
