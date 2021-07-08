package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.entities.Post;
import meli.bootcamp.desafio_spring.entities.Seller;
import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.SellerRepository;
import meli.bootcamp.desafio_spring.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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

    public FollowerCountDTO getFollowersCount(Long sellerId) {
        Seller seller = getSellerById(sellerId);
        return FollowerCountDTO.toDTO(seller);
    }

    public FollowersDTO getFollowers(Long sellerId, String orderParam) {
        Seller seller = getSellerById(sellerId);

        Sort sort = getSortByParamName(orderParam);
        List<User> followers = userRepository.findAllByFollowing_Id(sellerId, sort);

        return FollowersDTO.toDTO(seller, followers);
    }

    public FollowingDTO getFollowing(Long userId, String orderParam) {
        User user = getUserById(userId);

        Sort sort = getSortByParamName(orderParam);
        List<Seller> following = sellerRepository.findAllByFollowers_Id(userId, sort);

        return FollowingDTO.toDTO(user, following);
    }

    public PromotionalCountDTO getPromoProductsCount(Long sellerId) {
        Seller seller = getSellerById(sellerId);
        return PromotionalCountDTO.toDTO(seller);
    }

    public User getUserById(Long userId)  {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + userId + " was not found."));
    }

    public void followSeller(Long userId, Long sellerId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("The user with id " + userId + " doesn't exist."));

        Seller seller = this.sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("The seller with id " + sellerId + " doesn't exist."));

        user.followSeller(seller);
        seller.addFollower(user);

        try{
            this.userRepository.save(user);
            this.sellerRepository.save(seller);
        } catch (DataIntegrityViolationException e){
            throw new DuplicatedResourceException("The user with id " + userId + " already follows the seller " + sellerId);
        }
    }

    public void unfollowSeller(Long userId, Long sellerId) throws ResourceNotFoundException {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("The user with id " + userId + " doesn't exist."));

        Seller seller = this.sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResourceNotFoundException("The seller with id " + sellerId + " doesn't exist."));

        user.unfollowSeller(seller);

        this.userRepository.save(user);
        this.sellerRepository.save(seller);
    }

    public SellerPostsDTO getPosts(Long sellerId, boolean isPromo) {
        Seller seller = getSellerById(sellerId);
        return SellerPostsDTO.toDTO(seller, getPromotionalPostFilter(isPromo));
    }

    public Sort getSortByParamName(String paramName){
        if("name_asc".equalsIgnoreCase(paramName)){
            return sortByNameAsc();
        }else if("name_desc".equalsIgnoreCase(paramName)){
            return sortByNameDesc();
        }
        return null;
    }

    public Predicate<Post> getPromotionalPostFilter (boolean isPromo) {
        Predicate<Post> predicate = p -> true;
        if (isPromo) {
            predicate = p -> Objects.nonNull(p.getPromotion());
        }
        return predicate;

    }

    private Sort sortByNameAsc() {
        return Sort.by(Sort.Direction.ASC, "username");
    }

    private Sort sortByNameDesc() {
        return Sort.by(Sort.Direction.DESC, "username");
    }



}
