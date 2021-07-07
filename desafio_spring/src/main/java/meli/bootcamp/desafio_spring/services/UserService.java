package meli.bootcamp.desafio_spring.services;

import meli.bootcamp.desafio_spring.entities.User;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) throws ResourceNotFoundException {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + userId + " was not found."));
    }
}
