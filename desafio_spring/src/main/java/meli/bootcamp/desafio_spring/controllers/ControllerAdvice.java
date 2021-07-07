package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionDTO> handleResourceNotFoundException(ResponseStatusException ex){
        return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), ex.getStatus());
    }
}