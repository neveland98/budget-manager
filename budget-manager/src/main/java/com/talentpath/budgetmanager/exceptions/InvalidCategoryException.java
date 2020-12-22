package com.talentpath.budgetmanager.exceptions;

public class InvalidCategoryException extends Exception {
    public InvalidCategoryException(String message) {
        super(message);
    }
    public InvalidCategoryException(String message, Throwable innerException) {
        super(message,innerException);
    }
}
