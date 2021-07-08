package meli.bootcamp.desafio_spring.exceptions;

public class DuplicatedResourceException extends RuntimeException {
    public DuplicatedResourceException(String message){super(message);}
}