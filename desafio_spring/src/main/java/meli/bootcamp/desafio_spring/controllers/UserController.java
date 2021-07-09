package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.SellerService;
import meli.bootcamp.desafio_spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SellerService sellerService;

    public UserController(UserService userService, SellerService sellerService) {
        this.userService = userService;
        this.sellerService = sellerService;
    }

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public UserDTO getById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody CreateUserDTO createUserDTO){
        return userService.createUser(createUserDTO);
    }

    @PutMapping("{userId}")
    public UserDTO update(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @GetMapping("{userId}/followers/count")
    public FollowerCountDTO getFollowersCount(@PathVariable(value = "userId") Long sellerId){
        return sellerService.getFollowersCount(sellerId);
    }

    @GetMapping("{userId}/followed/count")
    public FollowingCountDTO getFollowingCount(@PathVariable Long userId){
        return userService.getFollowingCount(userId);
    }

    @GetMapping("{userId}/followers/list")
    public FollowersDTO getFollowers(
            @PathVariable(value = "userId") Long sellerId,
            @RequestParam(required = false) String order){
        return sellerService.getFollowers(sellerId, order);
    }

    @GetMapping("/{userId}/followed/list")
    public FollowingDTO getFollowing(
            @PathVariable Long userId,
            @RequestParam(required = false) String order){
        return userService.getFollowing(userId, order);
    }

    @PostMapping("{userId}/follow/{userIdToFollow}")
    public void followSeller(@PathVariable Long userId, @PathVariable(value = "userIdToFollow") Long sellerId){
        this.userService.followSeller(userId,sellerId);
    }

    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public void unfollowSeller(@PathVariable Long userId, @PathVariable(value = "userIdToUnfollow" ) Long sellerId) {
        this.userService.unfollowSeller(userId, sellerId);
    }
}
