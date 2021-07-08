package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.FollowerCountDTO;
import meli.bootcamp.desafio_spring.dtos.FollowersDTO;
import meli.bootcamp.desafio_spring.dtos.FollowingDTO;
import meli.bootcamp.desafio_spring.dtos.PromotionalCountDTO;
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

    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("Seller with id " + sellerId + " was not found."));
    }

    public FollowerCountDTO getFollowersCount(Long sellerId) throws ResourceNotFoundException {
        Seller seller = getSellerById(sellerId);
        return FollowerCountDTO.toDTO(seller);
    }

    public FollowersDTO getFollowers(Long sellerId) throws ResourceNotFoundException {
        Seller seller = getSellerById(sellerId);
        return FollowersDTO.toDTO(seller);
    }

    public FollowingDTO getFollowing(Long userId) throws ResourceNotFoundException{
        User user = getUserById(userId);
        return FollowingDTO.toDTO(user);
    }

    public PromotionalCountDTO getPromoProductsCount(Long sellerId){
        Seller seller = getSellerById(sellerId);
        return PromotionalCountDTO.toDTO(seller);
    }

    public User getUserById(Long userId) throws ResourceNotFoundException {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + userId + " was not found."));
    }

    public void followSeller(Long userId, Long sellerId){
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("The user with id " + userId + " doesn't exist.")
        );

        Seller seller = this.sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("The seller with id " + sellerId + " doesn't exist.")
        );

        user.followSeller(seller);
        seller.addFollower(user);

        this.userRepository.save(user);
        this.sellerRepository.save(seller);
    }
}
