package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.UserRepository;
import meli.bootcamp.desafio_spring.util.SortUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SellerService sellerService;

    public UserService(UserRepository userRepository, SellerService sellerService) {
        this.userRepository = userRepository;
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

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
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

        List<Seller> following = sellerService.findFollowing(userId, orderParam);

        return FollowingDTO.toDTO(user, following);
    }

    public List<User> findFollowers(Long sellerId, String orderParam) {
        Sort sort = SortUtils.getUserSorterOf(orderParam);
        return userRepository.findAllByFollowing_Id(sellerId, sort);
    }

    public PromotionalCountDTO getPromoProductsCount(Long sellerId) {
        Seller seller = sellerService.findSellerById(sellerId);
        return PromotionalCountDTO.toDTO(seller);
    }

    public void followSeller(Long userId, Long sellerId) {
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
        User user = findUserById(userId);
        Seller seller = sellerService.findSellerById(sellerId);

        user.unfollowSeller(seller);

        this.userRepository.save(user);
    }
}
