package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.CreateSellerDTO;
import meli.bootcamp.desafio_spring.dtos.FollowerCountDTO;
import meli.bootcamp.desafio_spring.dtos.FollowersDTO;
import meli.bootcamp.desafio_spring.dtos.SellerDTO;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.SellerRepository;
import meli.bootcamp.desafio_spring.util.SortUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final UserService userService;

    public SellerService(SellerRepository sellerRepository, UserService userService) {
        this.sellerRepository = sellerRepository;
        this.userService = userService;
    }

    public Seller findSellerById(Long sellerId)  {
        return this.sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("Seller with id " + sellerId + " was not found."));
    }

    public List<SellerDTO> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers.stream().map(SellerDTO::toDTO).collect(Collectors.toList());
    }

    public SellerDTO getSellerById(Long sellerId) {
        Seller seller = findSellerById(sellerId);
        return SellerDTO.toDTO(seller);
    }

    public SellerDTO createSeller(CreateSellerDTO createSellerDTO){
        Seller seller = new Seller(createSellerDTO.getUsername());
        Seller newSeller = sellerRepository.save(seller);
        return SellerDTO.toDTO(newSeller);
    }

    public SellerDTO updateSeller(Long sellerId, CreateSellerDTO sellerDTO) {
        Seller seller = findSellerById(sellerId);

        seller.setUsername(sellerDTO.getUsername());
        Seller updatedSeller = sellerRepository.save(seller);

        return SellerDTO.toDTO(updatedSeller);
    }

    public void deleteSellerById(Long sellerId) {
        Seller seller = findSellerById(sellerId);
        deleteSeller(seller);
    }

    public void deleteSeller(Seller seller){
        seller.getFollowers().forEach(f -> f.removeFollowing(seller));
        sellerRepository.delete(seller);
    }

    public FollowerCountDTO getFollowersCount(Long sellerId) {
        Seller seller = findSellerById(sellerId);
        return FollowerCountDTO.toDTO(seller);
    }

    public FollowersDTO getFollowers(Long sellerId, String orderParam) {
        Seller seller = findSellerById(sellerId);

       List<User> followers = userService.findFollowers(sellerId, orderParam);

        return FollowersDTO.toDTO(seller, followers);
    }

    public List<Seller> findFollowing(Long userId, String orderParam) {
        Sort sort = SortUtils.getUserSorterOf(orderParam);
        return sellerRepository.findAllByFollowers_Id(userId, sort);
    }
}
