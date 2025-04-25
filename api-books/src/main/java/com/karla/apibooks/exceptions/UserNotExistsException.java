package com.karla.apibooks.exceptions;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super("User not exists");
    }
}
