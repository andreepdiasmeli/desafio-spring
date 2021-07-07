package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.FollowerCountDTO;
import meli.bootcamp.desafio_spring.dtos.FollowersDTO;
import meli.bootcamp.desafio_spring.entities.Seller;
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

    private Seller getSellerById(Long sellerId) throws ResourceNotFoundException {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("Seller with id " + sellerId + " was not found."));
        return seller;
    }

    public FollowerCountDTO getFollowersCount(Long sellerId) throws ResourceNotFoundException {
        Seller seller = getSellerById(sellerId);
        return FollowerCountDTO.toDTO(seller);
    }

    public FollowersDTO getFollowers(Long sellerId) throws ResourceNotFoundException {
        Seller seller = getSellerById(sellerId);
        return FollowersDTO.toDTO(seller);
    }
}
