package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.ExceptionDTO;
import meli.bootcamp.desafio_spring.exceptions.DuplicatedResourceException;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
