package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.SellerRepository;
import meli.bootcamp.desafio_spring.repositories.UserRepository;
import meli.bootcamp.desafio_spring.util.SortUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;

    public UserService(UserRepository userRepository, SellerRepository sellerRepository, SellerService sellerService) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.sellerService = sellerService;
    }

    public User findUserById(Long userId)  {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + userId + " was not found."));
    }

    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return UserDTO.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    public UserDTO createUser(CreateUserDTO createUserDTO){
        User user = new User(createUserDTO.getUsername());
        User newUser = userRepository.save(user);
        return UserDTO.toDTO(newUser);
    }

    public UserDTO updateUser(Long userId, CreateUserDTO userDTO) {
        User user = findUserById(userId);

        user.setUsername(userDTO.getUsername());
        User updatedUser = userRepository.save(user);

        return UserDTO.toDTO(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = findUserById(userId);

        if(user instanceof Seller){
            sellerService.deleteSeller((Seller)user); return;
        }
        userRepository.delete(user);
    }

    public FollowingCountDTO getFollowingCount(Long userId) {
        User user = findUserById(userId);
        return FollowingCountDTO.toDTO(user);
    }

    public FollowingDTO getFollowing(Long userId, String orderParam) {
        User user = findUserById(userId);

        Sort sort = SortUtils.getUserSorterOf(orderParam);
        List<Seller> following = sellerRepository.findAllByFollowers_Id(userId, sort);

        return FollowingDTO.toDTO(user, following);
    }

    public FollowersDTO getFollowers(Long sellerId, String orderParam) {
        Seller seller = sellerService.findSellerById(sellerId);

        Sort sort = SortUtils.getUserSorterOf(orderParam);
        List<User> followers = userRepository.findAllByFollowing_Id(sellerId, sort);

        return FollowersDTO.toDTO(seller, followers);
    }

    public PromotionalCountDTO getPromoProductsCount(Long sellerId) {
        Seller seller = sellerService.findSellerById(sellerId);
        return PromotionalCountDTO.toDTO(seller);
    }

    public void followSeller(Long userId, Long sellerId) {
        if(userId == sellerId){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "An user can’t follow themselves.");
        }

        User user = findUserById(userId);
        Seller seller = sellerService.findSellerById(sellerId);

        user.followSeller(seller);

        try{
            this.userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new DuplicatedResourceException("The user with id " + userId + " already follows the seller " + sellerId);
        }
    }

    public void unfollowSeller(Long userId, Long sellerId) throws ResourceNotFoundException {
        if(!sellerRepository.existsByIdAndFollowers_Id(sellerId, userId)){
            throw new DuplicatedResourceException("The user with id " + userId + " is not following " + sellerId);
        }

        User user = findUserById(userId);
        Seller seller = sellerService.findSellerById(sellerId);

        user.unfollowSeller(seller);

        this.userRepository.save(user);
    }


    public void removePost(Post post) {
        Seller seller = post.getSeller();
        seller.removePost(post);
        this.sellerRepository.save(seller);
    }
}
