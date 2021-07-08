package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.ExceptionDTO;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionDTO> handleResponseStatusException(ResponseStatusException ex){
        return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), ex.getStatus());
    }

    @ExceptionHandler({ ResourceNotFoundException.class, DuplicatedResourceException.class })
    public ResponseEntity<ExceptionDTO> handleResourceExceptions(RuntimeException ex){
        return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ExceptionDTO> handleInvalidParameterException(InvalidParameterException ex){
        return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
