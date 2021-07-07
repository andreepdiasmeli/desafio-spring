package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
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

    public void followSeller(Long userId, Long userIdToFollow){
        User user = this.userRepository.findById(userId).orElseThrow(() ->
             new ResourceNotFoundException("The user with id " + userId + " doesn't exist.")
        );

        Seller seller = this.sellerRepository.findById(userIdToFollow).orElseThrow(() ->
                new ResourceNotFoundException("The seller with id " + userIdToFollow + " doesn't exist.")
        );

        user.followSeller(seller);
        seller.addFollower(user);

        this.userRepository.save(user);
        this.sellerRepository.save(seller);
    }

    public void unfollowSeller(Long userId, Long sellerId){

        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("The user with id " + userId + " doesn't exist.")
        );

        Seller seller = this.sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("The seller with id " + sellerId + " doesn't exist.")
        );

        user.unfollowSeller(seller);
        seller.removeFollower(user);

        this.userRepository.save(user);
        this.sellerRepository.save(seller);
    }

}
