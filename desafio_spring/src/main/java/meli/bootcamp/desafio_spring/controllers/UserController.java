package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.*;
import meli.bootcamp.desafio_spring.services.SellerService;
import meli.bootcamp.desafio_spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
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

    @Operation(summary = "Get all users")
    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAllUsers();
    }

    @Operation(summary = "Get a specific user by ID")
    @GetMapping("{userId}")
    public UserDTO getById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @Operation(summary = "Create a user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid CreateUserDTO createUserDTO){
        return userService.createUser(createUserDTO);
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("{userId}")
    public UserDTO update(@PathVariable Long userId, @RequestBody @Valid CreateUserDTO userDTO){
        return userService.updateUser(userId, userDTO);
    }

    @Operation(summary = "Delete an existing user")
    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @Operation(summary = "Get the count of followers by Seller ID")
    @GetMapping("{userId}/followers/count")
    public FollowerCountDTO getFollowersCount(@PathVariable(value = "userId") Long sellerId){
        return sellerService.getFollowersCount(sellerId);
    }

    @Operation(summary = "Get the count of sellers followed by User ID")
    @GetMapping("{userId}/followed/count")
    public FollowingCountDTO getFollowingCount(@PathVariable Long userId){
        return userService.getFollowingCount(userId);
    }

    @Operation(summary = "Get followers by Seller ID")
    @Parameter(
        name="order", 
        required = false, 
        description = "The order of the elements to be shown.", 
        example = "[\"name_asc\", \"name_desc\"")
    @GetMapping("{userId}/followers/list")
    public FollowersDTO getFollowers(
            @PathVariable(value = "userId") Long sellerId,
            @RequestParam(required = false) String order){
        return userService.getFollowers(sellerId, order);
    }

    @Parameter(
        name="order", 
        required = false, 
        description = "The order of the elements to be shown.", 
        example = "[\"name_asc\", \"name_desc\"")
    @Operation(summary = "Get sellers followed by User ID")
    @GetMapping("/{userId}/followed/list")
    public FollowingDTO getFollowing(
            @PathVariable Long userId,
            @RequestParam(required = false) String order){
        return userService.getFollowing(userId, order);
    }

    @Operation(summary = "Follow a specific Seller ID")
    @PostMapping("{userId}/follow/{userIdToFollow}")
    public void followSeller(@PathVariable Long userId, @PathVariable(value = "userIdToFollow") Long sellerId){
        this.userService.followSeller(userId,sellerId);
    }

    @Operation(summary = "Unfollow a specific Seller ID")
    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public void unfollowSeller(@PathVariable Long userId, @PathVariable(value = "userIdToUnfollow" ) Long sellerId) {
        this.userService.unfollowSeller(userId, sellerId);
    }
}
