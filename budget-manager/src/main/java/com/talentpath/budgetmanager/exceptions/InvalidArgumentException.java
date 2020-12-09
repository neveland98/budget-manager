package com.talentpath.budgetmanager.exceptions;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String message) {
        super(message);
    }
    public InvalidArgumentException(String message, Throwable innerException) {
        super(message,innerException);
    }

}
