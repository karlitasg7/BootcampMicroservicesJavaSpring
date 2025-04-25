package com.karla.apibooks.exceptions;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException() {
        super("Book not available");
    }
}
