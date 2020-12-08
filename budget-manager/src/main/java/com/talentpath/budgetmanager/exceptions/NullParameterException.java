package com.talentpath.budgetmanager.exceptions;

public class NullParameterException extends Exception {
    public NullParameterException(String message) {
        super(message);
    }
    public NullParameterException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
