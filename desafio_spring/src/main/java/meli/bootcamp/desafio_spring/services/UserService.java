package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.repositories.SellerRepository;
import meli.bootcamp.desafio_spring.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    public UserService(UserRepository userRepository, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
    }

    public Seller getSeller(Long userId) {
        return this.sellerRepository.getById(userId);
    }
}
