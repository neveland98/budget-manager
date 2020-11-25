package com.talentpath.budgetmanager.exceptions;

public class BudgetDaoException extends Exception {
    public BudgetDaoException(String message) {
        super(message);
    }
    public BudgetDaoException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
