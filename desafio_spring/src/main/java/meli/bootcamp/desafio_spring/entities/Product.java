package meli.bootcamp.desafio_spring.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;

    @ManyToOne
    @JoinColumn
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Seller seller;

    public Product() {
    }

    public Product(String name, String type, String brand, String color, String notes) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getNotes() {
        return notes;
    }

    public Category getCategory() {
        return category;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
