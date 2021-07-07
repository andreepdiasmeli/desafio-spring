package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.*;
import meli.bootcamp.desafio_spring.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DBService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DBService(UserRepository userRepository, PostRepository postRepository, PromotionRepository promotionRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void instantiateDB() {

        Seller seller1 = new Seller("André");
        Seller seller2 = new Seller("Bruno");
        Seller seller3 = new Seller("Gabriel");
        Seller seller4 = new Seller("Lucas");
        Seller seller5 = new Seller("Pierre");

        Post post1 = new Post(new BigDecimal("1.99"), seller1);
        Post post2 = new Post(new BigDecimal("3.95"), seller2);
        Post post3 = new Post(new BigDecimal("5.91"), seller3);
        Post post4 = new Post(new BigDecimal("7.85"), seller4);
        Post post5 = new Post(new BigDecimal("9.75"), seller5);

        seller1.setPosts(List.of(post1));
        seller2.setPosts(List.of(post2));
        seller3.setPosts(List.of(post3));
        seller4.setPosts(List.of(post4));
        seller5.setPosts(List.of(post5));

        Promotion promotion1 = new Promotion(new BigDecimal("0.25"), LocalDateTime.now().plusMonths(1), post3);
        Promotion promotion2 = new Promotion(new BigDecimal("0.15"), LocalDateTime.now().plusMonths(1), post4);
        Promotion promotion3 = new Promotion(new BigDecimal("0.05"), LocalDateTime.now().plusMonths(1), post5);

        post3.setPromotion(promotion1);
        post4.setPromotion(promotion2);
        post5.setPromotion(promotion3);

        Product product1 = new Product("Macbook", "Office", "Apple", "Silver", "Very Expensive");
        Product product2 = new Product("IPhone", "Personal", "Apple", "Pink", "Also Expensive");
        Product product3 = new Product("Table", "Office", "MadeiraMadeira", "Dark Wood", "Square");
        Product product4 = new Product("XBOX", "Gamer", "Microsoft", "White", "Much fun");
        Product product5 = new Product("PS5", "Gamer", "Sony", "Black", "Also much fun");


        product1.setPosts(List.of(post1));
        product2.setPosts(List.of(post2));
        product3.setPosts(List.of(post3));
        product4.setPosts(List.of(post4));
        product5.setPosts(List.of(post5));

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));

        post1.setProduct(product1);
        post2.setProduct(product2);
        post3.setProduct(product3);
        post4.setProduct(product4);
        post5.setProduct(product5);

        Category category1 = new Category("Technology");
        Category category2 = new Category("Video-games");
        Category category3 = new Category("Wood");

        category1.setProducts(List.of(product1, product2));
        category2.setProducts(List.of(product4, product5));
        category3.setProducts(List.of(product3));

        categoryRepository.saveAll(List.of(category1, category2, category3));

        product1.setCategory(category1);
        product2.setCategory(category1);
        product3.setCategory(category3);
        product4.setCategory(category2);
        product5.setCategory(category2);

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));

        User user1 = new User("Pacheco");
        User user2 = new User("Waldvogel");
        User user3 = new User("Nascimento");
        User user4 = new User("Neis");
        User user5 = new User("Calado");

        user1.setFollowing(List.of(seller1, seller2));
        user2.setFollowing(List.of(seller2, seller3));
        user3.setFollowing(List.of(seller3, seller4));
        user4.setFollowing(List.of(seller4, seller5));
        user5.setFollowing(List.of());

        seller1.setFollowers(List.of(user1));
        seller2.setFollowers(List.of(user1, user2));
        seller3.setFollowers(List.of(user2, user3));
        seller4.setFollowers(List.of(user3, user4));

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));
        userRepository.saveAll(List.of(seller1, seller2, seller3, seller4, seller5));

        product1.setSeller(seller1);
        product2.setSeller(seller2);
        product3.setSeller(seller3);
        product4.setSeller(seller4);
        product5.setSeller(seller5);

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
        seller5.setFollowers(List.of(user4));

        seller1.setProducts(List.of(product1));
        seller2.setProducts(List.of(product2));
        seller3.setProducts(List.of(product3));
        seller4.setProducts(List.of(product4));
        seller5.setProducts(List.of(product5));

        userRepository.saveAll(List.of(seller1, seller2, seller3, seller4, seller5));


    }
}
