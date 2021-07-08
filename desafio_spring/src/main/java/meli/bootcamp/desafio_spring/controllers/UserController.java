package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.FollowerCountDTO;
import meli.bootcamp.desafio_spring.dtos.FollowersDTO;
import meli.bootcamp.desafio_spring.dtos.FollowingDTO;
import meli.bootcamp.desafio_spring.services.UserService;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}/followers/count")
    public FollowerCountDTO getFollowersCount(@PathVariable(value = "userId") Long sellerId){
        return userService.getFollowersCount(sellerId);
    }

    @GetMapping("{userId}/followers/list")
    public FollowersDTO getFollowers(
            @PathVariable(value = "userId") Long sellerId,
            @RequestParam(required = false) String order){
        return userService.getFollowers(sellerId, order);
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
