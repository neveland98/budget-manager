package com.talentpath.budgetmanager.exceptions;

public class NullArgumentException extends Exception {
    public NullArgumentException(String message) {
        super(message);
    }
    public NullArgumentException(String message, Throwable innerException) {
        super(message,innerException);
    }
}
